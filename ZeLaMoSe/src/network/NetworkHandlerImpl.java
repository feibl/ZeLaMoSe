/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import network.NetworkHandler;
import domain.Step;
import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentLinkedQueue;
import network.Session;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class NetworkHandlerImpl extends NetworkHandler {

   private Handler handler;
   private Session session;
   private GameServer server;
   private ConcurrentLinkedQueue<NetworkMessage> updateQueue;

   public NetworkHandlerImpl(Handler handler, GameServer server) {
      this.handler = handler;
      this.server = server;
   }

   public NetworkHandlerImpl(Handler handler, GameServer server, ConcurrentLinkedQueue<NetworkMessage> updateQueue) {
      this.updateQueue = updateQueue;
      this.handler = handler;
      this.server = server;
   }

   public NetworkHandlerImpl(GameServer server) {
      //this.Handler = new LobbyHandler();
      this.server = server;
   }

   public NetworkHandlerImpl() {
   }

   @Override
   public SessionInformation getAddedSession() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public int getRandomGeneratorSeed() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public SessionInformation getRemovedSession() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public SessionInformation connectToServer(String nickname) throws RemoteException, ServerFullException {
      session = server.createSession(nickname, handler);
      return session.getSessionInformation();
   }

   @Override
   public void addStep(Step step) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public Step getStep() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void disconnectFromServer() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void requestForUpdate() {
      throw new UnsupportedOperationException("Not supported yet.");
   }
}
