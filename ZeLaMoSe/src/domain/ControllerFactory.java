/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import network.client.NetworkHandler;
import network.client.NetworkHandlerAbstract;

/**
 *
 * @author chrigi
 */
public class ControllerFactory {
    static public TetrisController getTetrisController(NetworkHandlerAbstract nh) {
        return new TetrisController(new SimulationController(), nh, new StepGenerator(new InputSampler()));
    }
    
    static public NetworkHandlerAbstract getNetworkHandler() {
        return new NetworkHandler();
    }
}
