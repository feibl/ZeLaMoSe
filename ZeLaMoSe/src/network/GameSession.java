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
class GameSession extends UnicastRemoteObject implements Session, ServerRemote{
   private SessionInformation sessionInformation;
   private GameServerImpl gameServer;
   private ClientRemote client;

   public GameSession(SessionInformation sessionInformation, GameServerImpl gameServer) throws RemoteException{
      this.sessionInformation = sessionInformation;
      this.gameServer = gameServer;
   }

   @Override
   public SessionInformation getSessionInformation() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void sendChatMessage(SessionInformation sender, String message) throws RemoteException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void sendStep(Step step) throws RemoteException {
      client.receiveStep(step);
   }

   @Override
   public void sendSessionAddedMessage(SessionInformation sessionInfo) throws RemoteException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void sendSessionRemovedMessage(SessionInformation sessionInfo) throws RemoteException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void sendStartSignal() throws RemoteException {
      // Throw Exception because Game already started?
   }

   @Override
   public void disconnect() throws RemoteException {
      gameServer.removeSession(this);
   }

   @Override
   public void receiveChatMessage(String message) throws RemoteException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void receiveStep(Step step) throws RemoteException {
      gameServer.distributeStepToOthers(this, step);
   }

   @Override
   public void receiveClientRemote(ClientRemote clientRemote) throws RemoteException {
      client = clientRemote;
   }
   
}
