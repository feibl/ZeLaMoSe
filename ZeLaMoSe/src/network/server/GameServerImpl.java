/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network.server;

import network.client.ClientRemote;
import domain.Step;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.ServerFullException;
import network.SessionInformation;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class GameServerImpl extends UnicastRemoteObject implements GameServer, GameServerRemote {

    Session[] sessionList;
    private static final int MAX_SESSIONS = 4;
    private static int id = 1;

    public GameServerImpl(String serverName, Registry registry) throws RemoteException, MalformedURLException {
        sessionList = new Session[MAX_SESSIONS];
        registry.rebind(serverName, this);
    }

    @Override
    public SessionRemote createSession(String nickname, ClientRemote clientRemote) throws RemoteException, ServerFullException {
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

    @Override
    public void startGame() {
        for (int i = 0; i < sessionList.length; i++) {
            Session s = sessionList[i];
            if (s != null) {
                try {
                    s.sendStartSignal();
                } catch (RemoteException ex) {
                    removeSession(s);
                }
            }
        }
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

    public void setSession(Session oldSession, Session newSession) {
        for (int i = 0; i < sessionList.length; i++) {
            Session s = sessionList[i];
            if (s != null && s == oldSession) {
                sessionList[i] = newSession;
            }
        }
    }

    void distributeStepToOthers(Session sender, Step step) {
        for (Session s : sessionList) {
            if (s != null && s != sender) {
                try {
                    s.sendStep(step);
                } catch (RemoteException ex) {
                    removeSession(s);
                }
            }
        }
    }
}