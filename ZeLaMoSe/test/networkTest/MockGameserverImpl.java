/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networkTest;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import network.GameServer;
import network.Handler;
import network.LobbySession;
import network.ServerFullException;
import network.Session;
import network.SessionInformation;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class MockGameserverImpl implements GameServer {

   private static int id = 1;
   List<Session> sessionList = new ArrayList<Session>();

   @Override
   public Session createSession(String nickname, Handler handler) throws RemoteException, ServerFullException {
      if (sessionList.size() < 4) {
         SessionInformation sessionInformation = new SessionInformation(id++, nickname);
         Session session = new LobbySession(sessionInformation, handler);
         sessionList.add(session);
         return session;
      }   
      else {
         throw new ServerFullException();
      }
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
