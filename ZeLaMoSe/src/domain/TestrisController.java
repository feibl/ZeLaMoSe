/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.Observable;

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
    //private NetworkHandler networkHandler = new
    //private StepGenerator
    public TestrisController(SimulationController sController/*, NetworkHandler nH, StepGenerator sG*/) {
        simulationController = sController;
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
