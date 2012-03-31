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
public class LobbyHandler extends UnicastRemoteObject implements ClientRemote, Handler {

   private NetworkHandlerImpl networkHandlerImpl;
   private ServerRemote serverRemote;

   public LobbyHandler(NetworkHandlerImpl networkHandler) throws RemoteException {
      this.networkHandlerImpl = networkHandler;
   }

   @Override
   public void notifyStep(Step step) throws RemoteException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void notifyChatMessage(ChatMessage message) {
      networkHandlerImpl.notifyChatMessageReceived(message);
   }

   @Override
   public void notifySessionAdded(SessionInformation session) {
      networkHandlerImpl.notifySessionAdded(session);
   }

   @Override
   public void notifySessionRemoved(SessionInformation session) {
      networkHandlerImpl.notifySessionRemoved(session);
   }

   @Override
   public void reportServerRemote(ServerRemote remote) {
      this.serverRemote = remote;
   }

   @Override
   public void disconnect() {
      try {
         serverRemote.disconnect();
      } catch (RemoteException ex) {
      }
   }

   @Override
   public void addChatMessage(String message) {
      try {
         serverRemote.addChatMessage(message);
      } catch (RemoteException ex) {
         networkHandlerImpl.notifyExceptionThrown(ex);
      }
   }

   @Override
   public void addStep(Step step) {
      //throw Exception?
   }

   @Override
   public void notifyGameStarted(ServerRemote newRemote) {
      GameHandler newHandler = null;
      try {
         newHandler = new GameHandler(networkHandlerImpl, newRemote);
      } catch (RemoteException ex) {
         Logger.getLogger(LobbyHandler.class.getName()).log(Level.SEVERE, null, ex);
      }
      networkHandlerImpl.setHandler(newHandler);
      try {
         newRemote.reportClientRemote(newHandler);
      } catch (RemoteException ex) {
         networkHandlerImpl.notifyExceptionThrown(ex);
      }
      networkHandlerImpl.notifyGameStarted();
   }
}
