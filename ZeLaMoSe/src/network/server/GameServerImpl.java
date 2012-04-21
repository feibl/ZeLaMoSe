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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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

    public GameServerImpl(String serverName, Registry registry) throws RemoteException, MalformedURLException {
    
        sessionList = new Session[MAX_SESSIONS];
                //GameServerImpl.class.getClass().getResource("/rmi.policy").getFile()
        File policy= Config.convertRMI(GameServerImpl.class);
        System.setProperty("java.security.policy", policy.getAbsolutePath() );
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
                try {
                    returnList.add(session.getSessionInformation());
                } catch (RemoteException ex) {
                    Logger.getLogger(GameServerImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
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

    protected void distributeStepToOthers(Session sender, Step step) {
        final Step stepFinal = step;
        for (final Session s : sessionList) {
            if (s != null && s != sender) {
                threadPool.execute(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            s.sendStep(stepFinal);
                        } catch (RemoteException ex) {
                            removeSession(s);
                        }
                    }
                });

            }
        }
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
        }
    }

    public void notifyReadySignalReceived(Session session) {
        if (getSessionList().size() == readyCount.incrementAndGet()) {
            notifyAllGameStarted();
        }
    }

    protected void notifyAllInitSignal(final long blockQueueSeed) {
        for (int i = 0; i < sessionList.length; i++) {
            final Session s = sessionList[i];
            if (s != null) {

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
        }
    }
}
