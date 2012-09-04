package networkTest;

import domain.Step;
import java.rmi.RemoteException;
import java.util.Collection;
import network.ChatMessage;
import network.GameParams;
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
    public void receiveInitSignal(GameParams gameParams) throws RemoteException {
        throw new RemoteException();
    }

    @Override
    public void receiveRestartSignal() throws RemoteException {
        throw new RemoteException();
    }
}
