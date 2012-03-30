/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import domain.StepInterface;
import domain.StepProducerInterface;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.util.Observable;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public abstract class NetworkHandler extends Observable implements StepInterface, StepProducerInterface {

   public enum UpdateType {

      STEP, SESSION_ADDED, SESSION_REMOVED
   };

   public abstract SessionInformation getAddedSession();

   public abstract int getRandomGeneratorSeed();

   public abstract SessionInformation getRemovedSession();

   public abstract SessionInformation connectToServer(String nickname, String serverName, String ip, ClientRemote clientRemote) throws Exception;

   public abstract void disconnectFromServer();

   public abstract void requestForUpdate();
}
