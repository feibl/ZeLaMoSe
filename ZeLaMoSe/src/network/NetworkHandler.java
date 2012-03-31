/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import domain.StepInterface;
import domain.StepProducerInterface;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Observable;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public abstract class NetworkHandler extends Observable implements StepInterface, StepProducerInterface {

  public enum UpdateType {

      STEP, SESSION_ADDED, SESSION_REMOVED, CONNECTION_ESTABLISHED, EXCEPTION_THROWN
   ,  CHAT_MESSAGE_RECEIVED, GAME_STARTED};

   public abstract SessionInformation getAddedSession();

   public abstract int getRandomGeneratorSeed();

   public abstract SessionInformation getRemovedSession();

   public abstract void connectToServer(String ip, String serverName, String nickname);

   public abstract void disconnectFromServer();
   
   public abstract SessionInformation getOwnSession();
   
   public abstract List<SessionInformation> getSessionList();
   
   public abstract void sendChatMessage(String message);
   
   public abstract ChatMessage getChatMessage();
   
   public abstract Exception getThrownException();
}
