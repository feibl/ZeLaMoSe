/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import domain.Step;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public interface ServerRemote extends Remote, Serializable {
   public void disconnect() throws RemoteException;
   public void addChatMessage(String message) throws RemoteException;
   public void addStep(Step step) throws RemoteException;
   public void reportClientRemote(ClientRemote clientRemote) throws RemoteException;
}
