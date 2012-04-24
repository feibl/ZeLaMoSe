/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networkTest;

import domain.Step;
import java.rmi.RemoteException;
import java.util.Collection;
import network.ChatMessage;
import network.client.ClientRemoteInterface;
import network.server.SessionRemoteInterface;
import network.SessionInformation;
import org.junit.Ignore;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
@Ignore
public class ClientRemoteAdapter implements ClientRemoteInterface {

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
    public void receiveInitSignal(long blockQueueSeed) throws RemoteException {
    }

    @Override
    public void receiveSteps(Collection<Step> steps) throws RemoteException {
    }
}
