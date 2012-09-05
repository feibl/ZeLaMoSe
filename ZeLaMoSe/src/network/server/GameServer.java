package network.server;

import domain.Config;
import domain.Step;
import java.io.File;
import java.net.MalformedURLException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.GameAlreadyStartedException;
import network.GameParams;
import network.ServerFullException;
import network.SessionInformation;
import network.client.ClientRemoteInterface;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class GameServer extends UnicastRemoteObject implements GameServerInterface, GameServerRemoteInterface {

    protected List<Session> sessionList;
    public static final int MAX_SESSIONS = 4;
    private static int id = 1;
    private AtomicInteger readyCount = new AtomicInteger(0);
    protected ExecutorService threadPool;
    private BlockingQueue<Step> receivedSteps = new LinkedBlockingQueue<Step>();
    public static final int STEP_DURATION = 50; //in millisecond
    private Semaphore currentNumberOfReceivedSteps = new Semaphore(0);
    private int currentStep = 0;
    private boolean gameStarted = false;
    private DiscoveryServer discoveryServer;

    public GameServer(String serverName, Registry registry) throws RemoteException, MalformedURLException {
        sessionList = new ArrayList<Session>(MAX_SESSIONS);
        File policy = Config.convertRMI(GameServer.class);
        System.setProperty("java.security.policy", policy.getAbsolutePath());
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }
        registry.rebind(serverName, this);
        discoveryServer = new DiscoveryServer();
    }

    @Override
    public synchronized SessionRemoteInterface createSession(String nickname, ClientRemoteInterface clientRemote) throws RemoteException, ServerFullException, GameAlreadyStartedException {
        SessionInformation sInfo = new SessionInformation(id++, nickname);
        if (gameStarted) {
            throw new GameAlreadyStartedException();
        } else if (sessionList.size() < MAX_SESSIONS) {
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
        for (Session session : sessionList) {
            if (session != null) {
                returnList.add(session.getSessionInformation());
            }
        }
        return returnList;
    }

    public synchronized void removeSession(Session session) {
        if (sessionList.contains(session)) {
            sessionList.remove(session);
            notifyAllSessionRemoved(session);
        }
    }

    protected void sendInitSignal(final Session s, final GameParams gameParams) {
        threadPool.execute(new Runnable() {

            @Override
            public void run() {
                try {
                    s.sendInitSignal(gameParams);
                } catch (RemoteException ex) {
                    removeSession(s);
                }
            }
        });
    }

    protected void sendStartSignal(final Session s) {
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

    protected void sendSteps(final Session s, final Collection<Step> removedSteps) {
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

    private void notifyAllSessionRemoved(Session session) {
        List<Session> copy = new ArrayList<Session>(sessionList);
        for (Session s : copy) {
            try {
                s.sendSessionRemovedMessage(session.getSessionInformation());
            } catch (RemoteException ex) {
                removeSession(s);
            }
        }
    }

    private void notifyOthersSessionAdded(Session session) {
        List<Session> copy = new ArrayList<Session>(sessionList);
        for (Session s : copy) {
            try {
                s.sendSessionAddedMessage(session.getSessionInformation());
            } catch (RemoteException ex) {
                removeSession(s);
            }
        }
    }

    public synchronized void postChatMessage(Session sender, String message) {
        List<Session> copy = new ArrayList<Session>(sessionList);
        for (Session s : copy) {
            try {
                s.sendChatMessage(sender.getSessionInformation(), message);
            } catch (RemoteException ex) {
                removeSession(s);
            }
        }
    }

    @Override
    public synchronized void startGame(long blockQueueSeed, int nbrOfJokers, boolean includeSpecialBlocks, int startLevel) {
        this.threadPool = Executors.newFixedThreadPool(sessionList.size());
        GameParams initParameter = new GameParams(blockQueueSeed, nbrOfJokers, includeSpecialBlocks, startLevel);
        gameStarted = true;
        notifyAllInitSignal(initParameter);
    }

    protected synchronized void notifyAllGameStarted() {
        List<Session> copy = new ArrayList<Session>(sessionList);
        for (Session s : copy) {
            sendStartSignal(s);
        }
    }

    //TODO 5 sec TimeOut
    public synchronized void notifyReadySignalReceived(Session session) {
        if (sessionList.size() == readyCount.incrementAndGet()) {
            notifyAllGameStarted();
            start();
        }
    }

    protected synchronized void notifyAllInitSignal(GameParams gameParams) {
        List<Session> copy = new ArrayList<Session>(sessionList);
        for (Session s : copy) {
            sendInitSignal(s, gameParams);
        }
    }

    public synchronized void addStep(Session sender, Step step) {
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
                allStepsReceived = currentNumberOfReceivedSteps.tryAcquire(sessionList.size(), 5000, TimeUnit.MILLISECONDS);
                aquireComplete = true;
            } catch (InterruptedException ex) {
                Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        synchronized (this) {
            if (!allStepsReceived) {
                currentNumberOfReceivedSteps.drainPermits();
                checkReceivedSteps();
            }
            Collection<Step> removedSteps = new ArrayList<Step>();

            receivedSteps.drainTo(removedSteps);
            currentStep++;

            List<Session> sessionListCopy = new ArrayList<Session>(sessionList);
            for (final Session s : sessionListCopy) {
                sendSteps(s, removedSteps);
            }
        }
    }

    private void checkReceivedSteps() {
        List<Session> sessionListCopy = new ArrayList<Session>(sessionList);
        for (Session session : sessionListCopy) {
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
                        Thread.sleep(STEP_DURATION);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    distributeSteps();
                }
            }
        }).start();
    }

    @Override
    public void startDiscoveryServer() {
        discoveryServer.start();
    }

    @Override
    public void stopDiscoveryServer() {
        discoveryServer.end();
    }
}
