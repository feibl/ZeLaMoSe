/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network.client;

import domain.Step;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import network.ChatMessage;
import network.server.SessionRemote;
import network.SessionInformation;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public interface ClientRemote extends Remote, Serializable {

   public void receiveStep(Step step) throws RemoteException;

   public void receiveChatMessage(ChatMessage message) throws RemoteException;

   public void receiveSessionAddedMessage(SessionInformation session) throws RemoteException;

   public void receiveSessionRemovedMessage(SessionInformation session) throws RemoteException;

   public void receiveStartSignal() throws RemoteException;

    public void receiveInitSignal() throws RemoteException;
}
