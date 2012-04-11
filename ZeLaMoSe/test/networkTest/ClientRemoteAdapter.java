/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networkTest;

import domain.Step;
import java.rmi.RemoteException;
import network.ChatMessage;
import network.client.ClientRemote;
import network.server.SessionRemote;
import network.SessionInformation;
import org.junit.Ignore;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
@Ignore
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
   public void receiveChatMessage(ChatMessage message) throws RemoteException {
   }

   @Override
   public void receiveStartSignal() throws RemoteException {
   }

    @Override
    public void receiveInitSignal() throws RemoteException {
    }
}
