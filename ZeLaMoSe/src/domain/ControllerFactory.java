package domain;

import network.client.NetworkHandler;
import network.client.NetworkHandlerAbstract;

/**
 *
 * @author Christian Mollekopf <cmolleko@hsr.ch>
 */
public class ControllerFactory {

    static public TetrisController getTetrisController(NetworkHandlerAbstract nh) {
        return new TetrisController(new SimulationController(), nh, new StepGenerator(new InputSampler()));
    }

    static public NetworkHandlerAbstract getNetworkHandler() {
        return new NetworkHandler();
    }
}
