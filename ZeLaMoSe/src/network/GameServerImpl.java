/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

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
   public SessionInformation createSession(String nickname, ClientRemote clientRemote) throws RemoteException, ServerFullException {
      SessionInformation sInfo = new SessionInformation(id++, nickname);
      for (int i = 0; i < sessionList.length; i++) {
         if (sessionList[i] == null) {
            LobbySession lobbySession = new LobbySession(sInfo, clientRemote);
            clientRemote.reportServerRemote(lobbySession);
            sessionList[i] = lobbySession;
            notifyOthersSessionAdded(lobbySession);
            return sInfo;
         }
      }
      throw new ServerFullException();
   }

   public List<SessionInformation> getSessionList() {
      List<SessionInformation> returnList = new ArrayList<SessionInformation>();
      for (Session session : sessionList) {
         returnList.add(session.getSessionInformation());
      }
      return returnList;
   }

   @Override
   public void startGame() {
      throw new UnsupportedOperationException("Not supported yet.");
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
               s.getClientRemote().notifySessionRemoved(session.getSessionInformation());
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
               s.getClientRemote().notifySessionAdded(session.getSessionInformation());
            } catch (RemoteException ex) {
               removeSession(s);
            }
         }
      }
   }
}
