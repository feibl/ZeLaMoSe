/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public interface Session extends Remote {

   public void sendMessage(NetworkMessage message) throws RemoteException;

   public void removeThisSession() throws RemoteException;

   public SessionInformation getSessionInformation() throws RemoteException;
}
