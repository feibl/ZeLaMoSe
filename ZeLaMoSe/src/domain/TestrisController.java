/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.interfaces.StepProducerInterface;
import domain.interfaces.SimulationStateInterface;
import java.util.Observable;
import java.util.Observer;
import java.util.TimerTask;
import java.util.Timer;
import network.NetworkHandler;

/**
 *
 * Wrap signals in Runnable, for synchronization to GUI thread.
 * notifyObservers will be called from the simulation thread (timer)
 * 
 * 
 * @author chrigi
 */
public class TestrisController extends Observable implements Observer {
    private Timer timer;
    enum UpdateType {
        GameStarted,
        SessionAdded,
        SessionRemoved
    }

    private SimulationController simulationController;
    private NetworkHandler networkHandler;
    private StepGenerator stepGenerator;
    
    private int currentStep = 0;
    private final int stepDuration = 50; //in millisecond
    
    public TestrisController(SimulationController sController, NetworkHandler nH/*, StepGenerator sG*/) {
        simulationController = sController;
        networkHandler = nH;
        stepGenerator.addObserver(this);
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

    @Override
    public void update(Observable o, Object o1) {
        if (o1 == NetworkHandler.UpdateType.STEP) {
            System.out.println("adding step: ");
            StepProducerInterface producer = (StepProducerInterface)o;
            Step step = producer.getStep();
            simulationController.addStep(step);
        }
    }
    
    
    /*
     * Called every 50ms
     * 
     * public for testing
     */
    public void runStep() {
        System.out.println("running step: "+currentStep+" time: "+System.nanoTime());
        stepGenerator.niggasInParis();
        simulationController.simulateStep(currentStep);
        currentStep++;
        
    }
    
    //Start the step timer
    public void run() { 
        TimerTask timerTask = new TimerTask() {
                                  @Override
                                  public void run() {
                                      runStep();
                                  }
                              };
        timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, stepDuration);
    }
    
    
    
}
