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
public interface GameServer extends Remote{
  public Session createSession(String nickname, Handler handler) throws RemoteException;
  public List<SessionInformation> getSessionList() throws RemoteException;
  public void startGame() throws RemoteException;
}
