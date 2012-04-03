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
      try {
         server.disconnect();
      } catch (RemoteException ex) {
         Logger.getLogger(GameHandler.class.getName()).log(Level.SEVERE, null, ex);
      }
   }

   @Override
   public void sendChatMessage(String message) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void sendStep(Step step) {
      try {
         server.receiveStep(step);
      } catch (RemoteException ex) {
         networkHandler.notifyExceptionThrown(ex);
      }
   }

   @Override
   public void receiveStep(Step step) throws RemoteException {
      networkHandler.notifyStepReceived(step);
   }

   @Override
   public void receiveChatMessage(ChatMessage message) throws RemoteException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void receiveSessionAddedMessage(SessionInformation session) throws RemoteException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void receiveSessionRemovedMessage(SessionInformation session) throws RemoteException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void receiveServerRemote(ServerRemote remote) throws RemoteException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void receiveStartSignal(ServerRemote remote) throws RemoteException {
      throw new UnsupportedOperationException("Not supported yet.");
   }
   
}
