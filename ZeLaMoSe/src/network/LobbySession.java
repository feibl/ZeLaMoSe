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

   private GameServerImpl gameServer;
   private SessionInformation sessionInformation;
   private ClientRemote client;

   public LobbySession(SessionInformation sessionInformation, ClientRemote client, GameServerImpl gameServer) throws RemoteException {
      this.sessionInformation = sessionInformation;
      this.client = client;
      this.gameServer = gameServer;
   }

   @Override
   public SessionInformation getSessionInformation() {
      return sessionInformation;
   }

   @Override
   public void disconnect() throws RemoteException {
      gameServer.removeSession(this);
   }

   @Override
   public void addChatMessage(String message) throws RemoteException {
      gameServer.postMessage(this, message);
   }

   @Override
   public void addStep(Step step) throws RemoteException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public List<SessionInformation> getOtherSessions() throws RemoteException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void sendMessage(SessionInformation sender, String message) throws RemoteException {
      client.notifyChatMessage(new ChatMessage(sender, message));
   }

   @Override
   public void sendStep(Step step) throws RemoteException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void sendSessionAddedMessage(SessionInformation sessionInfo) throws RemoteException {
      client.notifySessionAdded(sessionInfo);
   }

   @Override
   public void sendSessionRemovedMessage(SessionInformation sessionInfo) throws RemoteException {
      client.notifySessionRemoved(sessionInfo);
   }
}
