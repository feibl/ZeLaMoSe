/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.interfaces.SimulationStateInterface;
import domain.interfaces.StepProducerInterface;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import network.SessionInformation;
import network.client.NetworkHandler;
import network.server.GameServer;
import network.server.GameServerImpl;
import view.GameFieldJFrame;

/**
 *
 * Wrap signals in Runnable, for synchronization to GUI thread. notifyObservers will be called from the simulation
 * thread (timer)
 *
 *
 * @author chrigi
 */
public class TetrisController extends Observable implements Observer {

    public final static int SERVER_PORT = Registry.REGISTRY_PORT;
    public static final String SERVER_NAME = "TetrisServer";
    private Timer timer;
    private SimulationController simulationController;
    private NetworkHandler networkHandler;
    private StepGenerator stepGenerator;
    private int currentStep = 0;
    private final int stepDuration = 50; //in millisecond   
    private GameServer gameServer;
    private ConcurrentHashMap<Integer, String> sessionMap;
    private int localSessionID;

    public Object getThrownException() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public enum UpdateType {

        STEP, SESSION_ADDED, SESSION_REMOVED, CONNECTION_ESTABLISHED, EXCEPTION_THROWN, CHAT_MESSAGE_RECEIVED, GAME_STARTED
    };

    public TetrisController(SimulationController sController, NetworkHandler nH, StepGenerator sG) {
        simulationController = sController;
        networkHandler = nH;
        networkHandler.addObserver(this);
        stepGenerator = sG;
        stepGenerator.addObserver(this);
    }

    public Map<Integer, String> getSessionMap() {
        return sessionMap;
    }

    public GameFieldJFrame createGameFieldFrame() {
        List<SimulationStateInterface> otherSimulations = new ArrayList<SimulationStateInterface>();
        for (Integer sessionID : sessionMap.keySet()) {
            if (sessionID != localSessionID) {
                otherSimulations.add(simulationController.getSimulation(sessionID));
            }
        }
        SimulationStateInterface localSimulation = simulationController.getSimulation(localSessionID);;
        return new GameFieldJFrame(stepGenerator.getInputSampler(), localSimulation, otherSimulations);
    }

    public SimulationStateInterface getSession(int sessionId) {
        return simulationController.getSimulation(sessionId);
    }

    public void startServer() throws RemoteException, MalformedURLException {
        try {
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        } catch (RemoteException ex) {
        }
        Registry registry = LocateRegistry.getRegistry();
        gameServer = new GameServerImpl(TetrisController.SERVER_NAME, registry);
    }

    public void connectToServer(String ip, int port, String nickname) {
        networkHandler.connectToServer(ip, TetrisController.SERVER_NAME, nickname);
    }

    public void disconnectFromServer() {
        //TODO delete All Informations from the played Game
        networkHandler.deleteObserver(this);
        stepGenerator.deleteObserver(this);
        timer.cancel();
        networkHandler.disconnectFromServer();
    }

    public void startGame() {
        gameServer.startGame();
    }

    @Override
    public void update(Observable o, Object o1) {
        switch ((UpdateType) o1) {
            case STEP:
                StepProducerInterface producer = (StepProducerInterface) o;
                Step step = producer.getStep();
                simulationController.addStep(step);
                if (step.getSessionID() == localSessionID) {
                    networkHandler.addStep(step);
                }
                break;
            case CONNECTION_ESTABLISHED:
                localSessionID = networkHandler.getOwnSession().getId();
                sessionMap = networkHandler.getSessionList();
                                
                setChanged();
                notifyObservers(UpdateType.CONNECTION_ESTABLISHED);
                break;
            case GAME_STARTED:
                stepGenerator.setSessionID(localSessionID);
                for (Map.Entry<Integer, String> entry : sessionMap.entrySet()) {
                    // TODO getSeed()
                    simulationController.addSession(entry.getKey(), entry.getValue(), new GameEngine(entry.getKey(), 3));
                }
                
                setChanged();
                notifyObservers(UpdateType.GAME_STARTED);
                
                simulationController.initSimulation();
                run();
                break;
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
