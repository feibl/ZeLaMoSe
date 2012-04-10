/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network.server;

import network.client.ClientRemote;
import domain.Step;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import network.SessionInformation;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public interface SessionRemote extends Remote, Serializable {

    public void disconnect() throws RemoteException;

    public void receiveChatMessage(String message) throws RemoteException;

    public void receiveStep(Step step) throws RemoteException;

    public SessionInformation getSessionInformation() throws RemoteException;

    public void receiveReadySignal() throws RemoteException;
}
