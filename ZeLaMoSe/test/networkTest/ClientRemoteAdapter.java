package networkTest;

import domain.Step;
import java.rmi.RemoteException;
import java.util.Collection;
import network.ChatMessage;
import network.GameParams;
import network.SessionInformation;
import network.client.ClientRemoteInterface;
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
    public void receiveInitSignal(GameParams gameParams) throws RemoteException {
    }

    @Override
    public void receiveSteps(Collection<Step> steps) throws RemoteException {
    }

    @Override
    public void receiveRestartSignal() throws RemoteException {
    }
}
