package network.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import network.GameAlreadyStartedException;
import network.ServerFullException;
import network.SessionInformation;
import network.client.ClientRemoteInterface;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public interface GameServerRemoteInterface extends Remote {

    public SessionRemoteInterface createSession(String nickname, ClientRemoteInterface clientRemote) throws RemoteException, ServerFullException, GameAlreadyStartedException;

    public List<SessionInformation> getSessionList() throws RemoteException;
}
