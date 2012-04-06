/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network.client;

import domain.interfaces.StepInterface;
import domain.interfaces.StepProducerInterface;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import network.ChatMessage;
import network.SessionInformation;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public abstract class NetworkHandler extends Observable implements StepInterface, StepProducerInterface {



   public abstract SessionInformation getAddedSession();

   public abstract int getRandomGeneratorSeed();

   public abstract SessionInformation getRemovedSession();

   public abstract void connectToServer(String ip, String serverName, String nickname);

   public abstract void disconnectFromServer();
   
   public abstract SessionInformation getOwnSession();
   
   public abstract Map<Integer, String> getSessionList();
   
   public abstract void sendChatMessage(String message);
   
   public abstract ChatMessage getChatMessage();
   
   public abstract Exception getThrownException();
   
   public abstract ExecutorService getThreadPool();
}
