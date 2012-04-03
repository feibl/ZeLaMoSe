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
public class LobbyHandler extends UnicastRemoteObject implements ClientRemote, Handler {

   private NetworkHandlerImpl networkHandler;
   private ServerRemote server;

   public LobbyHandler(NetworkHandlerImpl networkHandler) throws RemoteException {
      this.networkHandler = networkHandler;
   }

   @Override
   public void receiveStep(Step step) throws RemoteException {
      //Do Nothing
   }

   @Override
   public void receiveChatMessage(ChatMessage message) {
      networkHandler.notifyChatMessageReceived(message);
   }

   @Override
   public void receiveSessionAddedMessage(SessionInformation session) {
      networkHandler.notifySessionAdded(session);
   }

   @Override
   public void receiveSessionRemovedMessage(SessionInformation session) {
      networkHandler.notifySessionRemoved(session);
   }

   @Override
   public void receiveServerRemote(ServerRemote remote) {
      this.server = remote;
   }

   @Override
   public void disconnect() {
      try {
         server.disconnect();
      } catch (RemoteException ex) {
      }
   }

   @Override
   public void sendChatMessage(String message) {
      try {
         server.receiveChatMessage(message);
      } catch (RemoteException ex) {
         networkHandler.notifyExceptionThrown(ex);
      }
   }

   @Override
   public void sendStep(Step step) {
      networkHandler.notifyExceptionThrown(new GameNotStartedException());
   }

   @Override
   public void receiveStartSignal(ServerRemote newRemote) {
      GameHandler newHandler = null;
      try {
         newHandler = new GameHandler(networkHandler, newRemote);
      } catch (RemoteException ex) {
         Logger.getLogger(LobbyHandler.class.getName()).log(Level.SEVERE, null, ex);
      }
      networkHandler.setHandler(newHandler);
      try {
         newRemote.receiveClientRemote(newHandler);
      } catch (RemoteException ex) {
         networkHandler.notifyExceptionThrown(ex);
      }
      networkHandler.notifyGameStarted();
   }
}
