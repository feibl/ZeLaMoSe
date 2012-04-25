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
import network.ServerFullException;
import network.SessionInformation;
import network.client.ClientRemoteInterface;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class GameServer extends UnicastRemoteObject implements GameServerInterface, GameServerRemoteInterface {

    protected SessionInterface[] sessionList;
    private static final int MAX_SESSIONS = 4;
    private static int id = 1;
    private AtomicInteger readyCount = new AtomicInteger(0);
    protected ExecutorService threadPool;
    private BlockingQueue<Step> receivedSteps = new LinkedBlockingQueue<Step>();
    private Timer timer = new Timer();
    private final int stepDuration = 50; //in millisecond   
    private Semaphore currentNumberOfReceivedSteps = new Semaphore(0);;
    private Semaphore mutex = new Semaphore(1, true);
            
    public GameServer(String serverName, Registry registry) throws RemoteException, MalformedURLException {

        sessionList = new SessionInterface[MAX_SESSIONS];
        File policy = Config.convertRMI(GameServer.class);
        System.setProperty("java.security.policy", policy.getAbsolutePath());
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }
        registry.rebind(serverName, this);
    }

    public GameServer() throws RemoteException {
        sessionList = new SessionInterface[MAX_SESSIONS];
    }

    @Override
    public synchronized SessionRemoteInterface createSession(String nickname, ClientRemoteInterface clientRemote) throws RemoteException, ServerFullException {
        SessionInformation sInfo = new SessionInformation(id++, nickname);
        for (int i = 0; i < sessionList.length; i++) {
            if (sessionList[i] == null) {
                Session newSession = new Session(sInfo, clientRemote, this);
                notifyOthersSessionAdded(newSession);
                sessionList[i] = newSession;
                return newSession;
            }
        }
        throw new ServerFullException();
    }

    @Override
    public List<SessionInformation> getSessionList() {
        List<SessionInformation> returnList = new ArrayList<SessionInformation>();
        for (SessionInterface session : sessionList) {
            if (session != null) {
                returnList.add(session.getSessionInformation());
            }
        }
        return returnList;
    }

    public void removeSession(SessionInterface session) {
        for (int i = 0; i < sessionList.length; i++) {
            if (sessionList[i] == session) {
                sessionList[i] = null;
                break;
            }
        }
        notifyAllSessionRemoved(session);
    }

    protected void sendInitSignal(final SessionInterface s, final long blockQueueSeed) {
        threadPool.execute(new Runnable() {

            @Override
            public void run() {
                try {
                    s.sendInitSignal(blockQueueSeed);
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
        for (int i = 0; i < sessionList.length; i++) {
            SessionInterface s = sessionList[i];
            if (s != null) {
                try {
                    s.sendSessionRemovedMessage(session.getSessionInformation());
                } catch (RemoteException ex) {
                    removeSession(s);
                }
            }
        }
    }

    private void notifyOthersSessionAdded(SessionInterface session) {
        for (int i = 0; i < sessionList.length; i++) {
            SessionInterface s = sessionList[i];
            if (s != null && s != session) {
                try {
                    s.sendSessionAddedMessage(session.getSessionInformation());
                } catch (RemoteException ex) {
                    removeSession(s);
                }
            }
        }
    }

    public void postChatMessage(SessionInterface sender, String message) {
        for (SessionInterface s : sessionList) {
            if (s != null) {
                try {
                    s.sendChatMessage(sender.getSessionInformation(), message);
                } catch (RemoteException ex) {
                    removeSession(s);
                }
            }

        }
    }

    protected synchronized void addStep(SessionInterface sender, Step step) {
        System.out.println("addStep"+ sender.getSessionInformation() + " step " + step.getSequenceNumber());
       
            currentNumberOfReceivedSteps.release();
        try {
            mutex.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        receivedSteps.add(step);
        mutex.release();
    }

    @Override
    public void startGame() {
        this.threadPool = Executors.newFixedThreadPool(getSessionList().size());
        long blockQueueSeed = new Random().nextLong();
        notifyAllInitSignal(blockQueueSeed);
    }

    protected void notifyAllGameStarted() {
        for (int i = 0; i < sessionList.length; i++) {
            final SessionInterface s = sessionList[i];
            if (s != null) {
                sendStartSignal(s);
            }
        }
    }

    //TODO 5 sec TimeOut
    public void notifyReadySignalReceived(SessionInterface session) {
        if (getSessionList().size() == readyCount.incrementAndGet()) {
            notifyAllGameStarted();
            start();
        }
    }

    protected void notifyAllInitSignal(final long blockQueueSeed) {
        for (int i = 0; i < sessionList.length; i++) {
            final SessionInterface s = sessionList[i];
            if (s != null) {
                sendInitSignal(s, blockQueueSeed);
            }
        }
    }

    public void distributeSteps() {
        try {
            currentNumberOfReceivedSteps.acquire(getSessionList().size());
            mutex.acquire();
                    final Collection<Step> removedSteps = new ArrayList<Step>();
        receivedSteps.drainTo(removedSteps);
        for (final SessionInterface s : sessionList) {
            if (s != null) {
                System.out.println("distributeSteps"+ s.getSessionInformation() + " steps " + removedSteps.toString());
                sendSteps(s, removedSteps);
            }
        }
        mutex.release();
        } catch (InterruptedException ex) {
            Logger.getLogger(GameServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        //TODO TimerTask + blockieren in der for schleife
    }

    private void start() {
        TimerTask stepDistributionTask = new TimerTask() {
            @Override
            public void run() {
                distributeSteps();
            }
        };
        timer.scheduleAtFixedRate(stepDistributionTask, 1000, stepDuration);
    }
}
