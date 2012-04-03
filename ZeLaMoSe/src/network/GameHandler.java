/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import domain.Step;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
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
      networkHandler.notifyExceptionThrown(new GameAlreadyStartedException());
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
      //Do Nothing
   }

   @Override
   public void receiveSessionAddedMessage(SessionInformation session) throws RemoteException {
      //Do Nothing
   }

   @Override
   public void receiveSessionRemovedMessage(SessionInformation session) throws RemoteException {
      networkHandler.notifySessionRemoved(session);
   }

   @Override
   public void receiveServerRemote(ServerRemote remote) throws RemoteException {
      //Do Nothing
   }

   @Override
   public void receiveStartSignal(ServerRemote remote) throws RemoteException {
      //Do Nothing
   }
   
}
