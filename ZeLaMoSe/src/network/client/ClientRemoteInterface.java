package network.client;

import domain.Step;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;
import network.ChatMessage;
import network.GameParams;
import network.SessionInformation;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public interface ClientRemoteInterface extends Remote, Serializable {

    public void receiveSteps(Collection<Step> steps) throws RemoteException;

    public void receiveChatMessage(ChatMessage message) throws RemoteException;

    public void receiveSessionAddedMessage(SessionInformation session) throws RemoteException;

    public void receiveSessionRemovedMessage(SessionInformation session) throws RemoteException;

    public void receiveStartSignal() throws RemoteException;

    public void receiveInitSignal(GameParams gameParams) throws RemoteException;
}
