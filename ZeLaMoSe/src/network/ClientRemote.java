/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import domain.Step;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public interface ClientRemote extends Remote, Serializable {

   public void receiveStep(Step step) throws RemoteException;

   public void receiveChatMessage(ChatMessage message) throws RemoteException;

   public void receiveSessionAddedMessage(SessionInformation session) throws RemoteException;

   public void receiveSessionRemovedMessage(SessionInformation session) throws RemoteException;

   public void receiveServerRemote(ServerRemote remote) throws RemoteException;

   public void receiveStartSignal(ServerRemote remote) throws RemoteException;
}
