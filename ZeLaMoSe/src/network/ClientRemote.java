/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import domain.Step;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public interface ClientRemote extends Remote {
   public void notifyStep(Step step) throws RemoteException;
   public void notifyChatMessage(String sender, String message) throws RemoteException;
   public void notifySessionAdded(SessionInformation session) throws RemoteException;
   public void notifySessionRemoved(SessionInformation session) throws RemoteException;
   public void reportServerRemote(ServerRemote remote) throws RemoteException;
}
