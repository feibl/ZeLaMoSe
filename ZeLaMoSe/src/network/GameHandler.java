/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import domain.Step;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
      try {
         server.addStep(step);
      } catch (RemoteException ex) {
         networkHandler.notifyExceptionThrown(ex);
      }
   }

   @Override
   public void notifyStep(Step step) throws RemoteException {
      networkHandler.notifyStepReceived(step);
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
