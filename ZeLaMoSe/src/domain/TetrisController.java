/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.interfaces.SimulationStateInterface;
import domain.interfaces.StepProducerInterface;
import java.rmi.registry.Registry;
import java.util.Observable;
import java.util.Observer;
import java.util.TimerTask;
import java.util.Timer;
import network.client.NetworkHandler;

/**
 *
 * Wrap signals in Runnable, for synchronization to GUI thread. notifyObservers will be called from the simulation
 * thread (timer)
 *
 *
 * @author chrigi
 */
public class TetrisController implements Observer {

    public enum UpdateType {

        STEP, SESSION_ADDED, SESSION_REMOVED, CONNECTION_ESTABLISHED, EXCEPTION_THROWN, CHAT_MESSAGE_RECEIVED, GAME_STARTED
    };

    public final static int SERVER_PORT = Registry.REGISTRY_PORT;
    public static final String SERVER_NAME = "TetrisServer";
     
    private Timer timer;   
    private SimulationController simulationController;
    private NetworkHandler networkHandler;
    private StepGenerator stepGenerator;
    private int currentStep = 0;
    private final int stepDuration = 50; //in millisecond
    private int localSessionID;
    

    public TetrisController(SimulationController sController, NetworkHandler nH, StepGenerator sG) {
        localSessionID = nH.getOwnSession().getId();
        simulationController = sController;
        networkHandler = nH;
        networkHandler.addObserver(this);
        stepGenerator = sG;
        stepGenerator.addObserver(this);
    }
    
    public SimulationStateInterface getSession(int sessionId) {
        return simulationController.getSimulation(sessionId);
    }

    public void startGame() {
        simulationController.initSimulation();
        run();
    }

    @Override
    public void update(Observable o, Object o1) {
        if (o1 == UpdateType.STEP) {
            StepProducerInterface producer = (StepProducerInterface) o;
            Step step = producer.getStep();                   
            simulationController.addStep(step);
            if (step.getSessionID() == localSessionID) {
                networkHandler.addStep(step);
            }
        }
    }

    /*
     * Called every 50ms
     *
     * public for testing
     */
    public void runStep() {
        System.out.println("running step: " + currentStep + " time: " + System.nanoTime());        
        simulationController.simulateStep(currentStep);
        stepGenerator.niggasInParis();
        currentStep++;

    }
    
    public void runFirstStep() {
        System.out.println("first step: " + currentStep + " time: " + System.nanoTime());        
        stepGenerator.niggasInParis();
        currentStep++;

    }

    //Start the step timer
    private void run() {
        TimerTask firstStepTask = new TimerTask() {

            @Override
            public void run() {
                runFirstStep();
            }
            
        };
        TimerTask stepTask = new TimerTask() {

            @Override
            public void run() {
                runStep();
            }
        };
        timer = new Timer();
        timer.schedule(firstStepTask, 0);
        timer.scheduleAtFixedRate(stepTask, stepDuration, stepDuration);
    }
}
