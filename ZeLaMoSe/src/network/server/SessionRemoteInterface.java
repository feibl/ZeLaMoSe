package network.server;

import domain.Step;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import network.SessionInformation;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public interface SessionRemoteInterface extends Remote, Serializable {

    public void disconnect() throws RemoteException;

    public void receiveChatMessage(String message) throws RemoteException;

    public void receiveStep(Step step) throws RemoteException;

    public SessionInformation getRemoteSessionInformation() throws RemoteException;

    public void receiveReadySignal() throws RemoteException;

    public void receiveRestartRequest() throws RemoteException;
}
