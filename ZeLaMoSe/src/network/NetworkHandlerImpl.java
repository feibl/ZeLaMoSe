/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import network.NetworkHandler;
import domain.Step;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class NetworkHandlerImpl extends NetworkHandler {

   private Handler handler;
   private Step lastStep;
   private ConcurrentLinkedQueue<SessionInformation> addedSessions = new ConcurrentLinkedQueue<SessionInformation>();
   private ConcurrentLinkedQueue<SessionInformation> removedSessions = new ConcurrentLinkedQueue<SessionInformation>();
   private ConcurrentLinkedQueue<Step> steps = new ConcurrentLinkedQueue<Step>();
   private SessionInformation lastAddedSession;
   private SessionInformation lastRemovedSession;
   private SessionInformation ownSession;
   private ExecutorService threadPool;

   public void setHandler(Handler handler) {
      this.handler = handler;
   }

   public NetworkHandlerImpl() {
      threadPool = Executors.newFixedThreadPool(1);
   }

   @Override
   public SessionInformation getAddedSession() {
      return lastAddedSession;
   }

   @Override
   public int getRandomGeneratorSeed() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public SessionInformation getRemovedSession() {
      return lastRemovedSession;
   }

   @Override
   public void connectToServer(final String nickname, final String serverName, final String ip) {
      threadPool.submit(new ConnectionRunnable(this, ip, serverName, nickname));
   }

   @Override
   public void addStep(Step step) throws RemoteException {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public Step getStep() {
      return lastStep;
   }

   @Override
   public void disconnectFromServer() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   public void notifyStepReceived(Step step) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void requestForUpdate() {
//      if (!updateQueue.isEmpty()) {
//         NetworkMessage message = updateQueue.poll();
//         UpdateType updateType = null;
//         if (message instanceof SessionAddedMessage) {
//            lastAddedSession = (SessionInformation) message.getMessageObject();
//            updateType = UpdateType.SESSION_ADDED;
//         }
//         if (message instanceof SessionRemovedMessage) {
//            lastRemovedSession = (SessionInformation) message.getMessageObject();
//            updateType = UpdateType.SESSION_REMOVED;
//         }
//         setChanged();
//         notifyObservers(updateType);
//      }
   }

   public void notifySessionAdded(SessionInformation addedSession) {
      lastAddedSession = addedSession;
      setChanged();
      notifyObservers();
      lastAddedSession = null;
   }

   public void notifySessionRemoved(SessionInformation removedSession) {
      lastRemovedSession = removedSession;
      setChanged();
      notifyObservers();
      lastAddedSession = null;
   }

   void notifyConnectionEstablished(SessionInformation session) {
      ownSession = session;
      setChanged();
      notifyObservers(UpdateType.CONNECTION_ESTABLISHED);
   }

   void notifyExceptionThrown(Exception ex) {
      setChanged();
      notifyObservers(UpdateType.EXCEPTION_THROWN);
   }

   @Override
   public SessionInformation getOwnSession() {
      return ownSession;
   }
}
