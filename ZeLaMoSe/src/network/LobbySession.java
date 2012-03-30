/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import domain.Step;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class LobbySession extends UnicastRemoteObject implements ServerRemote, Session {

   private SessionInformation sessionInformation;
   private ClientRemote client;
   
   public LobbySession(SessionInformation sessionInformation, ClientRemote client) throws RemoteException {
      this.sessionInformation = sessionInformation;
      this.client = client;
   }

   @Override
   public SessionInformation getSessionInformation() {
      return sessionInformation;
   }

   /**
    * @return the handler
    */
   @Override
   public ClientRemote getClientRemote() {
      return client;
   }

   @Override
   public void disconnect() throws RemoteException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void addChatMessage(String message) throws RemoteException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void addStep(Step step) throws RemoteException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public List<SessionInformation> getOtherSessions() throws RemoteException {
      throw new UnsupportedOperationException("Not supported yet.");
   }
   
}
