/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networkTest;

import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentLinkedQueue;
import network.Handler;
import network.NetworkMessage;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class MockLobbyHandler implements Handler{
   private ConcurrentLinkedQueue<NetworkMessage> updateQueue;

   MockLobbyHandler(ConcurrentLinkedQueue<NetworkMessage> updateQueue) {
      this.updateQueue = updateQueue;
   }

   MockLobbyHandler() {
      
   }

   @Override
   public void receiveMessage(NetworkMessage message) throws RemoteException {
      updateQueue.add(message);
   }
   
}
