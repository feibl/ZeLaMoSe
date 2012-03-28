/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import domain.Step;
import domain.StepInterface;
import java.net.InetAddress;
import java.rmi.RemoteException;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class NetworkHandlerImpl extends NetworkHandler implements ClientHandler {

  @Override
  public Session getAddedSession() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public int getRandomGeneratorSeed() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public Session getRemovedSession() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void connectToServer(InetAddress address) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void addStep(Step step) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void receiveStep(Step step) throws RemoteException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void receiveStartGameEvent() throws RemoteException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void receiveRandomGeneratorSeed(int seed) throws RemoteException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void notifySessionAdded(int sessionID, String nickname) throws RemoteException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void notifySessionRemoved(int sessionID, String nickname) throws RemoteException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public Step getStep() {
    throw new UnsupportedOperationException("Not supported yet.");
  }
  
}
