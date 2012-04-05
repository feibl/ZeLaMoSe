/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network.server;

import network.client.ClientRemote;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import network.ServerFullException;
import network.SessionInformation;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public interface GameServerRemote extends Remote {
  public SessionRemote createSession(String nickname, ClientRemote clientRemote) throws RemoteException, ServerFullException;
  public List<SessionInformation> getSessionList() throws RemoteException;
}
