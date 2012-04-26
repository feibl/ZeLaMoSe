/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networkTest;

import domain.Step;
import java.rmi.RemoteException;
import java.util.Collection;
import network.ChatMessage;
import network.SessionInformation;
import network.client.ClientRemoteInterface;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class ClientRemoteUnreachable implements ClientRemoteInterface {

    @Override
    public void receiveSteps(Collection<Step> steps) throws RemoteException {
        throw new RemoteException();
    }

    @Override
    public void receiveChatMessage(ChatMessage message) throws RemoteException {
        throw new RemoteException();
    }

    @Override
    public void receiveSessionAddedMessage(SessionInformation session) throws RemoteException {
        throw new RemoteException();
    }

    @Override
    public void receiveSessionRemovedMessage(SessionInformation session) throws RemoteException {
        throw new RemoteException();
    }

    @Override
    public void receiveStartSignal() throws RemoteException {
        throw new RemoteException();
    }

    @Override
    public void receiveInitSignal(long blockQueueSeed) throws RemoteException {
        throw new RemoteException();
    }
}
