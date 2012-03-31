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
class GameHandler extends UnicastRemoteObject implements Handler, ClientRemote{
   private final NetworkHandlerImpl networkHandler;
   private final ServerRemote server;

   public GameHandler(NetworkHandlerImpl networkHandlerImpl, ServerRemote remote) throws RemoteException {
      this.networkHandler = networkHandlerImpl;
      this.server = remote;
   }

   @Override
   public void disconnect() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void addChatMessage(String message) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void addStep(Step step) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void notifyStep(Step step) throws RemoteException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void notifyChatMessage(ChatMessage message) throws RemoteException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void notifySessionAdded(SessionInformation session) throws RemoteException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void notifySessionRemoved(SessionInformation session) throws RemoteException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void reportServerRemote(ServerRemote remote) throws RemoteException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void notifyGameStarted(ServerRemote remote) throws RemoteException {
      throw new UnsupportedOperationException("Not supported yet.");
   }
   
}
