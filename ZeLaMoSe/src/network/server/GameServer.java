/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network.server;

import domain.Config;
import domain.Step;
import java.io.File;
import java.net.MalformedURLException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.GameAlreadyStartedException;
import network.ServerFullException;
import network.SessionInformation;
import network.client.ClientRemoteInterface;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class GameServer extends UnicastRemoteObject implements GameServerInterface, GameServerRemoteInterface {

    protected List<SessionInterface> sessionList;
    private static final int MAX_SESSIONS = 4;
    private static int id = 1;
    private AtomicInteger readyCount = new AtomicInteger(0);
    protected ExecutorService threadPool;
    private BlockingQueue<Step> receivedSteps = new LinkedBlockingQueue<Step>();
    private final int stepDuration = 50; //in millisecond
    private Semaphore currentNumberOfReceivedSteps = new Semaphore(0);
    private int currentStep = 0;
    private boolean gameStarted = false;

    public GameServer(String serverName, Registry registry) throws RemoteException, MalformedURLException {
        this();
        File policy = Config.convertRMI(GameServer.class);
        System.setProperty("java.security.policy", policy.getAbsolutePath());
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }
        registry.rebind(serverName, this);
    }

    public GameServer() throws RemoteException {
        sessionList = new ArrayList<SessionInterface>(MAX_SESSIONS);
    }

    @Override
    public synchronized SessionRemoteInterface createSession(String nickname, ClientRemoteInterface clientRemote) throws RemoteException, ServerFullException, GameAlreadyStartedException {
        SessionInformation sInfo = new SessionInformation(id++, nickname);
        if(gameStarted) {
            throw new GameAlreadyStartedException();
        }
        else if (sessionList.size() < MAX_SESSIONS) {
            Session newSession = new Session(sInfo, clientRemote, this);
            notifyOthersSessionAdded(newSession);
            sessionList.add(newSession);
            return newSession;
        }
        throw new ServerFullException();
    }

    @Override
    public synchronized List<SessionInformation> getSessionList() {
        List<SessionInformation> returnList = new ArrayList<SessionInformation>();
        for (SessionInterface session : sessionList) {
            if (session != null) {
                returnList.add(session.getSessionInformation());
            }
        }
        return returnList;
    }

    public synchronized void removeSession(SessionInterface session) {
        if (sessionList.contains(session)) {
            sessionList.remove(session);
            notifyAllSessionRemoved(session);
        }
    }

    protected void sendInitSignal(final SessionInterface s, final long blockQueueSeed, final int numberOfJokers) {
        threadPool.execute(new Runnable() {

            @Override
            public void run() {
                try {
                    s.sendInitSignal(blockQueueSeed,numberOfJokers);
                } catch (RemoteException ex) {
                    removeSession(s);
                }
            }
        });
    }

    protected void sendStartSignal(final SessionInterface s) {
        threadPool.execute(new Runnable() {

            @Override
            public void run() {
                try {
                    s.sendStartSignal();
                } catch (RemoteException ex) {
                    removeSession(s);
                }
            }
        });
    }

    protected void sendSteps(final SessionInterface s, final Collection<Step> removedSteps) {
        threadPool.execute(new Runnable() {

            @Override
            public void run() {
                try {
                    s.sendSteps(removedSteps);
                } catch (RemoteException ex) {
                    removeSession(s);
                }
            }
        });
    }

    private void notifyAllSessionRemoved(SessionInterface session) {
        List<SessionInterface> copy = new ArrayList<SessionInterface>(sessionList);
        for (SessionInterface s : copy) {
            try {
                s.sendSessionRemovedMessage(session.getSessionInformation());
            } catch (RemoteException ex) {
                removeSession(s);
            }
        }
    }

    private void notifyOthersSessionAdded(SessionInterface session) {
        List<SessionInterface> copy = new ArrayList<SessionInterface>(sessionList);
        for (SessionInterface s : copy) {
            try {
                s.sendSessionAddedMessage(session.getSessionInformation());
            } catch (RemoteException ex) {
                removeSession(s);
            }
        }
    }

    public synchronized void postChatMessage(SessionInterface sender, String message) {
        List<SessionInterface> copy = new ArrayList<SessionInterface>(sessionList);
        for (SessionInterface s : copy) {
            try {
                s.sendChatMessage(sender.getSessionInformation(), message);
            } catch (RemoteException ex) {
                removeSession(s);
            }
        }
    }

    @Override
    public synchronized void startGame(int numberOfJokers) {
        this.threadPool = Executors.newFixedThreadPool(sessionList.size());
        long blockQueueSeed = new Random().nextLong();
        gameStarted = true;
        notifyAllInitSignal(blockQueueSeed, numberOfJokers);
    }

    protected synchronized void notifyAllGameStarted() {
        List<SessionInterface> copy = new ArrayList<SessionInterface>(sessionList);
        for (SessionInterface s : copy) {
            sendStartSignal(s);
        }
    }

    //TODO 5 sec TimeOut
    public synchronized void notifyReadySignalReceived(SessionInterface session) {
        if (sessionList.size() == readyCount.incrementAndGet()) {
            notifyAllGameStarted();
            start();
        }
    }

    protected synchronized void notifyAllInitSignal(final long blockQueueSeed, int numberOfJokers) {
        List<SessionInterface> copy = new ArrayList<SessionInterface>(sessionList);
        for (SessionInterface s : copy) {
            sendInitSignal(s, blockQueueSeed,numberOfJokers);
        }
    }

    public synchronized void addStep(SessionInterface sender, Step step) {
        if (step.getSequenceNumber() == currentStep && sessionList.contains(sender)) {
            receivedSteps.add(step);
            currentNumberOfReceivedSteps.release();
        }
    }

    public void distributeSteps() {
        boolean allStepsReceived = false;
        boolean aquireComplete = false;

        while (!aquireComplete) {
            try {
                //blocated
                //currentNumberOfReceivedSteps.acquire(sessionList.size());
                //not blocated
                allStepsReceived = currentNumberOfReceivedSteps.tryAcquire(sessionList.size(), 5000, TimeUnit.MILLISECONDS);
                aquireComplete = true;
            } catch (InterruptedException ex) {
                Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        synchronized (this) {            
            if (!allStepsReceived) {
                System.out.println("GameServer: remove Session. Stepqueue size: " + receivedSteps.size());
                checkReceivedSteps();
            }
            Collection<Step> removedSteps = new ArrayList<Step>();

            receivedSteps.drainTo(removedSteps);
            currentStep++;
            
            List<SessionInterface> sessionListCopy = new ArrayList<SessionInterface>(sessionList);
            for (final SessionInterface s : sessionListCopy) {
                sendSteps(s, removedSteps);
            }
        }
    }

    private void checkReceivedSteps() {
        List<SessionInterface> sessionListCopy = new ArrayList<SessionInterface>(sessionList);
        for (SessionInterface session : sessionListCopy) {
            boolean stepReceived = false;
            for (Step step : receivedSteps) {
                if (step.getSessionID() == session.getSessionInformation().getId()) {
                    stepReceived = true;
                    break;
                }
            }
            if (!stepReceived) {
                removeSession(session);
            }
        }
    }

    private void start() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(stepDuration);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    distributeSteps();
                }
            }
        }).start();
    }

}
