/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import domain.Step;
import java.rmi.RemoteException;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public interface Session {

   public SessionInformation getSessionInformation();

   public void sendChatMessage(SessionInformation sender, String message) throws RemoteException;

   public void sendStep(Step step) throws RemoteException;

   public void sendSessionAddedMessage(SessionInformation sessionInfo) throws RemoteException;

   public void sendSessionRemovedMessage(SessionInformation sessionInfo) throws RemoteException;

   public void sendStartSignal() throws RemoteException;
}
