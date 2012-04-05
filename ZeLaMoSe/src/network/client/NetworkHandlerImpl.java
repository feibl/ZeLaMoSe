/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network.client;

import network.DisconnectionRunnable;
import network.ConnectionRunnable;
import network.client.NetworkHandler;
import domain.Step;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import domain.TetrisController.UpdateType;
import network.AddStepRunnable;
import network.ChatMessage;
import network.SendChatMessageRunnable;
import network.SessionInformation;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class NetworkHandlerImpl extends NetworkHandler {

   private Handler handler;
   private Step lastStep;
   private SessionInformation lastAddedSession;
   private SessionInformation lastRemovedSession;
   private SessionInformation ownSession;
   private ChatMessage chatMessage;
   private ExecutorService threadPool;
   private Map<Integer, SessionInformation> sessionList = new HashMap<Integer, SessionInformation>();
   private Exception thrownException;

    @Override
    public void niggasInParis() {
        //call notify observers from here => hand all data over to TetrisController for the nex simulation step
        throw new UnsupportedOperationException("Not supported yet.");
    }  
   
   public void setHandler(Handler handler) {
      this.handler = handler;
   }

   public Handler getHandler() {
      return handler;
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
   public void connectToServer(final String ip, final String serverName, final String nickname) {
      threadPool.submit(new ConnectionRunnable(this, ip, serverName, nickname));
   }

   @Override
   public void addStep(Step step) {
      threadPool.execute(new AddStepRunnable(step, handler));
   }

   @Override
   public Step getStep() {
      return lastStep;
   }

   @Override
   public void disconnectFromServer() {
      threadPool.execute(new DisconnectionRunnable(handler));
   }

   public void notifyStepReceived(Step step) {
      lastStep = step;
      setChanged();
      notifyObservers(UpdateType.STEP);
   }

   public void notifySessionAdded(SessionInformation addedSession) {
      lastAddedSession = addedSession;
      sessionList.put(addedSession.getId(), addedSession);
      setChanged();
      notifyObservers(UpdateType.SESSION_ADDED);
   }

   public void notifySessionRemoved(SessionInformation removedSession) {
      lastRemovedSession = removedSession;
      sessionList.remove(new Integer(removedSession.getId()));
      setChanged();
      notifyObservers(UpdateType.SESSION_REMOVED);
   }

   public void notifyExceptionThrown(Exception ex) {
      thrownException = ex;
      setChanged();
      notifyObservers(UpdateType.EXCEPTION_THROWN);
   }

   @Override
   public SessionInformation getOwnSession() {
      return ownSession;
   }

   public void notifyConnectionEstablished(SessionInformation ownSession, List<SessionInformation> sessionList) {
      this.ownSession = ownSession;
      for(SessionInformation session: sessionList) {
         this.sessionList.put(session.getId(), session);
      }
      setChanged();
      notifyObservers(UpdateType.CONNECTION_ESTABLISHED);
   }

   @Override
   public List<SessionInformation> getSessionList() {
      return new ArrayList<SessionInformation>(sessionList.values());
   }

   @Override
   public void sendChatMessage(final String message) {
      threadPool.execute(new SendChatMessageRunnable(message, handler));    
   }

   void notifyChatMessageReceived(ChatMessage message) {
      this.chatMessage = message;
      setChanged();
      notifyObservers(UpdateType.CHAT_MESSAGE_RECEIVED);
   }

   @Override
   public ChatMessage getChatMessage() {
      return chatMessage;
   }

   @Override
   public Exception getThrownException() {
      return thrownException;
   }

   void notifyGameStarted() {
      setChanged();
      notifyObservers(UpdateType.GAME_STARTED);
   }

   @Override
   public ExecutorService getThreadPool() {
      return threadPool;
   }
}
