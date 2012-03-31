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
   public void notifyStep(Step step) throws RemoteException {
   }

   @Override
   public void notifySessionAdded(SessionInformation session) throws RemoteException {
   }

   @Override
   public void notifySessionRemoved(SessionInformation session) throws RemoteException {
   }

   @Override
   public void reportServerRemote(ServerRemote remote) throws RemoteException {
   }

   @Override
   public void notifyChatMessage(ChatMessage message) throws RemoteException {
   }

   @Override
   public void notifyGameStarted(ServerRemote remote) throws RemoteException {
   }
}
