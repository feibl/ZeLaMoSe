/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.interfaces.SimulationStateInterface;
import domain.interfaces.StepProducerInterface;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Observable;
import java.util.Observer;
import java.util.TimerTask;
import java.util.Timer;
import network.client.NetworkHandler;
import network.SessionInformation;
import network.server.GameServer;
import network.server.GameServerImpl;
import sun.org.mozilla.javascript.ast.CatchClause;

/**
 *
 * Wrap signals in Runnable, for synchronization to GUI thread. notifyObservers will be called from the simulation
 * thread (timer)
 *
 *
 * @author chrigi
 */
public class TetrisController extends Observable implements Observer {

    public enum UpdateType {

        STEP, SESSION_ADDED, SESSION_REMOVED, CONNECTION_ESTABLISHED, EXCEPTION_THROWN, CHAT_MESSAGE_RECEIVED, GAME_STARTED
    };
    private Timer timer;
    public final static int SERVER_PORT = Registry.REGISTRY_PORT;
    private SimulationController simulationController;
    private NetworkHandler networkHandler;
    private StepGenerator stepGenerator;
    private GameServer gameServer;
    private int currentStep = 0;
    private final int stepDuration = 50; //in millisecond
    private int localSessionID = -1;
    public static final String SERVER_NAME = "TetrisServer";

    public TetrisController(SimulationController sController, NetworkHandler nH, StepGenerator sG) {
        localSessionID = nH.getOwnSession().getId();
        simulationController = sController;
        networkHandler = nH;
        networkHandler.addObserver(this);
        stepGenerator = sG;
        stepGenerator.addObserver(this);
    }

//    public Map<Integer, String> getAvailableSessions() {
//        
//    }
    public SimulationStateInterface getSession(int sessionId) {
        return simulationController.getSimulation(sessionId);
    }

    public void startServer() throws RemoteException, MalformedURLException {
        try {
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        } catch (RemoteException ex) {
        }
        Registry registry = LocateRegistry.getRegistry();
        gameServer = new GameServerImpl(SERVER_NAME, registry);
    }

    public void connectToServer(String ip, int port) {
        //TODO Nickname zulassen
        networkHandler.connectToServer(ip, SERVER_NAME, "nickname");
    }

    public void startGame() {
        //gameServer.startGame();
        simulationController.initSimulation();
        run();
    }

    @Override
    public void update(Observable o, Object o1) {
        System.out.println("update");
        if (o1 == UpdateType.STEP) {
            StepProducerInterface producer = (StepProducerInterface) o;
            Step step = producer.getStep();
            System.out.println(" adding step: " + step.getSessionID() + " " + step.getSequenceNumber());                      
            simulationController.addStep(step);
            if (step.getSessionID() == localSessionID) {
                System.out.println(" sending step");
                networkHandler.addStep(step);
            }
        }
//        if (o1 == UpdateType.GAME_STARTED) {
//            System.out.println("starting game");
//            simulationController.initSimulation();
//            run();
//        }
//
//        if (o1 == UpdateType.SESSION_ADDED) {
//            System.out.println("session added");
//        }
//
//        if (o1 == UpdateType.SESSION_REMOVED) {
//            System.out.println("session removed");
//        }
//        if (o1 == UpdateType.CONNECTION_ESTABLISHED) {
//            System.out.println("connection established");
//            SessionInformation sessionInformation = networkHandler.getOwnSession();
//            localSessionID = sessionInformation.getId();
//            stepGenerator.setSessionID(sessionInformation.getId());
//            simulationController.addSession(localSessionID, "localSessionName", new GameEngine(localSessionID));
//        }



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
    
    public void firstStep() {
        System.out.println("first step: " + currentStep + " time: " + System.nanoTime());        
        stepGenerator.niggasInParis();
        currentStep++;

    }

    //Start the step timer
    private void run() {
        TimerTask firstTimerTask = new TimerTask() {

            @Override
            public void run() {
                firstStep();
            }
            
        };
        TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {
                runStep();
            }
        };
        timer = new Timer();
        timer.schedule(firstTimerTask, 0);
        timer.scheduleAtFixedRate(timerTask, 2000, stepDuration);
    }
}
