/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.Observable;
import network.NetworkHandler;

/**
 *
 * @author chrigi
 */
public class TestrisController extends Observable {
    
    enum UpdateType {
        GameStarted,
        SessionAdded,
        SessionRemoved
    }

    private SimulationController simulationController;
    private NetworkHandler networkHandler;
    //private StepGenerator
    
    public TestrisController(SimulationController sController, NetworkHandler nH/*, StepGenerator sG*/) {
        simulationController = sController;
        networkHandler = nH;
    }
    
//    public Map<Integer, String> getAvailableSessions() {
//        
//    }
    
    public SimulationStateInterface getSession(int sessionId) {
        return simulationController.getSimulation(sessionId);
    }
    
    void startServer() {
        //TODO create server and connectToServer
    }
    
    void connectToServer(String ip, int port) {
        //TODO connect networkhandler to server
    }
    
    void startGame() {
        //Trigger startGame in server
    }
    
    
    
}
