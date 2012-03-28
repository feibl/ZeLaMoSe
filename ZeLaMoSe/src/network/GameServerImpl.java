/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import view.LobbyJFrame;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class GameServerImpl extends UnicastRemoteObject implements GameServer{
   List<Session> sessionList = new ArrayList<Session>();
   private static int id = 1;
   
   public GameServerImpl() throws RemoteException {
      
   }

   @Override
   public Session createSession(String nickname, Handler handler) throws RemoteException {
      LobbySession newSession = new LobbySession(new SessionInformation(id++, nickname), handler);
      sessionList.add(newSession);
      return newSession;
   }

   @Override
   public List<SessionInformation> getSessionList() throws RemoteException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void startGame() throws RemoteException {
      throw new UnsupportedOperationException("Not supported yet.");
   }
   
}
