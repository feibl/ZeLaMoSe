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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
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

    protected List<SessionInterface> sessionArrayList;
    private static final int MAX_SESSIONS = 4;
    private static int id = 1;
    private AtomicInteger readyCount = new AtomicInteger(0);
    protected ExecutorService threadPool;
    private BlockingQueue<Step> receivedSteps = new LinkedBlockingQueue<Step>();
    private Timer timer = new Timer();
    private final int stepDuration = 50; //in millisecond   

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
        sessionArrayList = new ArrayList<SessionInterface>(MAX_SESSIONS);
    }

    @Override
    public synchronized SessionRemoteInterface createSession(String nickname, ClientRemoteInterface clientRemote) throws RemoteException, ServerFullException {
        SessionInformation sInfo = new SessionInformation(id++, nickname);
        if (sessionArrayList.size() < MAX_SESSIONS) {
            Session newSession = new Session(sInfo, clientRemote, this);
            notifyOthersSessionAdded(newSession);
            sessionArrayList.add(newSession);
            return newSession;
        }
        throw new ServerFullException();
    }

    @Override
    public synchronized List<SessionInformation> getSessionList() {
        List<SessionInformation> returnList = new ArrayList<SessionInformation>();
        for (SessionInterface session : sessionArrayList) {
            if (session != null) {
                returnList.add(session.getSessionInformation());
            }
        }
        return returnList;
    }

    public synchronized void removeSession(SessionInterface session) {
        if (sessionArrayList.contains(session)) {
            sessionArrayList.remove(session);
            notifyAllSessionRemoved(session);
        }
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
        List<SessionInterface> copy = new ArrayList<SessionInterface>(sessionArrayList);
        for (SessionInterface s : copy) {
            try {
                s.sendSessionRemovedMessage(session.getSessionInformation());
            } catch (RemoteException ex) {
                removeSession(s);
            }
        }
    }

    private void notifyOthersSessionAdded(SessionInterface session) {
        List<SessionInterface> copy = new ArrayList<SessionInterface>(sessionArrayList);
        for (SessionInterface s : copy) {
            try {
                s.sendSessionAddedMessage(session.getSessionInformation());
            } catch (RemoteException ex) {
                removeSession(s);
            }
        }
    }

    public synchronized void postChatMessage(SessionInterface sender, String message) {
        List<SessionInterface> copy = new ArrayList<SessionInterface>(sessionArrayList);
        for (SessionInterface s : copy) {
            try {
                s.sendChatMessage(sender.getSessionInformation(), message);
            } catch (RemoteException ex) {
                removeSession(s);
            }
        }
    }

    protected void addStep(SessionInterface sender, Step step) {
        receivedSteps.add(step);
    }

    @Override
    public synchronized void startGame() {
        this.threadPool = Executors.newFixedThreadPool(getSessionList().size());
        long blockQueueSeed = new Random().nextLong();
        notifyAllInitSignal(blockQueueSeed);
    }

    protected synchronized void notifyAllGameStarted() {
        List<SessionInterface> copy = new ArrayList<SessionInterface>(sessionArrayList);
        for (SessionInterface s : copy) {
            sendStartSignal(s);
        }
    }

    //TODO 5 sec TimeOut
    public synchronized void notifyReadySignalReceived(SessionInterface session) {
        if (getSessionList().size() == readyCount.incrementAndGet()) {
            notifyAllGameStarted();
            start();
        }
    }

    protected synchronized void notifyAllInitSignal(final long blockQueueSeed) {
        List<SessionInterface> copy = new ArrayList<SessionInterface>(sessionArrayList);
        for (SessionInterface s : copy) {
            sendInitSignal(s, blockQueueSeed);
        }
    }

    public void distributeSteps() {
        List<SessionInterface> copy = new ArrayList<SessionInterface>(sessionArrayList);
        final Collection<Step> removedSteps = new ArrayList<Step>();
        receivedSteps.drainTo(removedSteps);

        for (final SessionInterface s : copy) {
            sendSteps(s, removedSteps);
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
