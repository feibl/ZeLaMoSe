/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network.server;

import domain.Step;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;
import network.SessionInformation;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public interface Session {

   public SessionInformation getSessionInformation();

   public void sendChatMessage(SessionInformation sender, String message) throws RemoteException;

   public void sendSteps(Collection<Step> steps) throws RemoteException;

   public void sendSessionAddedMessage(SessionInformation sessionInfo) throws RemoteException;

   public void sendSessionRemovedMessage(SessionInformation sessionInfo) throws RemoteException;

   public void sendStartSignal() throws RemoteException;

    public void sendInitSignal(long blockQueueSeed) throws RemoteException;
}
