/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networkTest;

import domain.Step;
import java.rmi.RemoteException;
import network.ChatMessage;
import network.ClientRemote;
import network.ServerRemote;
import network.SessionInformation;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class ClientRemoteAdapter implements ClientRemote {

   @Override
   public void receiveStep(Step step) throws RemoteException {
   }

   @Override
   public void receiveSessionAddedMessage(SessionInformation session) throws RemoteException {
   }

   @Override
   public void receiveSessionRemovedMessage(SessionInformation session) throws RemoteException {
   }

   @Override
   public void receiveServerRemote(ServerRemote remote) throws RemoteException {
   }

   @Override
   public void receiveChatMessage(ChatMessage message) throws RemoteException {
   }

   @Override
   public void receiveStartSignal(ServerRemote remote) throws RemoteException {
   }
}
