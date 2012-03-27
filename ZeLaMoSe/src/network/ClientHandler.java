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
public interface ClientHandler extends Remote{
  public void receiveStep(Step step) throws RemoteException;
  public void receiveStartGameEvent() throws RemoteException;
  public void receiveRandomGeneratorSeed(int seed) throws RemoteException;
  public void notifySessionAdded(int sessionID, String nickname) throws RemoteException;
  public void notifySessionRemoved(int sessionID, String nickname) throws RemoteException;
}
