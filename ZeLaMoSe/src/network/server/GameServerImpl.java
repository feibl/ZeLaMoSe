/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network.server;

import domain.Config;
import network.client.ClientRemote;
import domain.Step;
import java.io.File;
import java.net.MalformedURLException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.ServerFullException;
import network.SessionInformation;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class GameServerImpl extends UnicastRemoteObject implements GameServer, GameServerRemote {

    protected Session[] sessionList;
    private static final int MAX_SESSIONS = 4;
    private static int id = 1;
    private AtomicInteger readyCount = new AtomicInteger(0);
    protected ExecutorService threadPool;
    private BlockingQueue<Step> receivedSteps = new LinkedBlockingQueue<Step>();
    private Timer timer = new Timer();
    private final int stepDuration = 50; //in millisecond   

    public GameServerImpl(String serverName, Registry registry) throws RemoteException, MalformedURLException {

        sessionList = new Session[MAX_SESSIONS];
        //GameServerImpl.class.getClass().getResource("/rmi.policy").getFile()
        File policy = Config.convertRMI(GameServerImpl.class);
        System.setProperty("java.security.policy", policy.getAbsolutePath());
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }
        registry.rebind(serverName, this);
    }

    public GameServerImpl() throws RemoteException {
        sessionList = new Session[MAX_SESSIONS];
    }

    @Override
    public synchronized SessionRemote createSession(String nickname, ClientRemote clientRemote) throws RemoteException, ServerFullException {
        SessionInformation sInfo = new SessionInformation(id++, nickname);
        for (int i = 0; i < sessionList.length; i++) {
            if (sessionList[i] == null) {
                SessionImpl newSession = new SessionImpl(sInfo, clientRemote, this);
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
        for (Session session : sessionList) {
            if (session != null) {
                returnList.add(session.getSessionInformation());
            }
        }
        return returnList;
    }

    public void removeSession(Session session) {
        for (int i = 0; i < sessionList.length; i++) {
            if (sessionList[i] == session) {
                sessionList[i] = null;
                break;
            }
        }
        notifyAllSessionRemoved(session);
    }

    protected void sendInitSignal(final Session s, final long blockQueueSeed) {
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
        for (int i = 0; i < sessionList.length; i++) {
            Session s = sessionList[i];
            if (s != null) {
                try {
                    s.sendSessionRemovedMessage(session.getSessionInformation());
                } catch (RemoteException ex) {
                    removeSession(s);
                }
            }
        }
    }

    private void notifyOthersSessionAdded(Session session) {
        for (int i = 0; i < sessionList.length; i++) {
            Session s = sessionList[i];
            if (s != null && s != session) {
                try {
                    s.sendSessionAddedMessage(session.getSessionInformation());
                } catch (RemoteException ex) {
                    removeSession(s);
                }
            }
        }
    }

    public void postChatMessage(Session sender, String message) {
        for (Session s : sessionList) {
            if (s != null) {
                try {
                    s.sendChatMessage(sender.getSessionInformation(), message);
                } catch (RemoteException ex) {
                    removeSession(s);
                }
            }

        }
    }

    protected void addStep(Session sender, Step step) {
        receivedSteps.add(step);
    }

    @Override
    public void startGame() {
        this.threadPool = Executors.newFixedThreadPool(getSessionList().size());
        long blockQueueSeed = new Random().nextLong();
        notifyAllInitSignal(blockQueueSeed);
    }

    protected void notifyAllGameStarted() {
        for (int i = 0; i < sessionList.length; i++) {
            final Session s = sessionList[i];
            if (s != null) {
                sendStartSignal(s);
            }
        }
    }

    //TODO 5 sec TimeOut
    public void notifyReadySignalReceived(Session session) {
        if (getSessionList().size() == readyCount.incrementAndGet()) {
            notifyAllGameStarted();
            start();
        }
    }

    protected void notifyAllInitSignal(final long blockQueueSeed) {
        for (int i = 0; i < sessionList.length; i++) {
            final Session s = sessionList[i];
            if (s != null) {
                sendInitSignal(s, blockQueueSeed);
            }
        }
    }

    private void distributeSteps() {
        final Collection<Step> removedSteps = new ArrayList<Step>();
        receivedSteps.drainTo(removedSteps);
        for(Step s: removedSteps) {
            System.out.println(s.getSequenceNumber() + ": " + s.getSessionID());
        }
        for (final Session s : sessionList) {
            if (s != null) {
                sendSteps(s, removedSteps);
            }
        }
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
