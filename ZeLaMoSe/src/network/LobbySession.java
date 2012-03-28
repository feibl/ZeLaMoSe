/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
class LobbySession extends UnicastRemoteObject implements Session {

   private SessionInformation sessionInformation;
   private Handler handler;
   
   LobbySession(SessionInformation sessionInformation, Handler handler) throws RemoteException {
      this.sessionInformation = sessionInformation;
      this.handler = handler;
   }
   


   @Override
   public void sendMessage(NetworkMessage message) throws RemoteException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void removeThisSession() throws RemoteException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public SessionInformation getSessionInformation() throws RemoteException {
      return sessionInformation;
   }
   
}
