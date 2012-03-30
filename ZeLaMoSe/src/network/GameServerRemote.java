/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public interface GameServerRemote extends Remote {
  public SessionInformation createSession(String nickname, ClientRemote clientRemote) throws RemoteException, ServerFullException;
}
