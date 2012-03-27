/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import domain.StepInterface;
import domain.StepProducerInterface;
import java.net.InetAddress;
import java.util.Observable;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public abstract class NetworkHandler extends Observable implements StepInterface, StepProducerInterface {
  public abstract Session getAddedSession();
  public abstract int getRandomGeneratorSeed();
  public abstract Session getRemovedSession();
  public abstract void connectToServer(InetAddress address) throws Exception;
}
