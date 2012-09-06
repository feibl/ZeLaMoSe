package domain;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import network.GameParams;
import network.client.NetworkHandlerAbstract;
import network.server.GameServer;
import network.server.GameServerInterface;

/**
 *
 * Wrap signals in Runnable, for synchronization to GUI thread. notifyObservers will be called from the simulation
 * thread (timer)
 *
 *
 * @author Christian Mollekopf <cmolleko@hsr.ch>
 */
public class TetrisController extends Observable implements Observer {

    public final static int SERVER_PORT = Registry.REGISTRY_PORT;
    public static final String SERVER_NAME = "TetrisServer";
    private ConcurrentHashMap<Integer, String> sessionMap;
    private SimulationController simulationController;
    private NetworkHandlerAbstract networkHandler;
    private StepGenerator stepGenerator;
    private GameServerInterface gameServer;
    private volatile boolean gameRunning;
    private Exception thrownException;
    private int localSessionID = -1;
    private boolean autorun = true;
    private ReplayData replayData;
    private int currentStep = 0;

    public enum UpdateType {

        STEP, SESSION_ADDED, SESSION_REMOVED, CONNECTION_ESTABLISHED, EXCEPTION_THROWN, CHAT_MESSAGE_RECEIVED, GAME_STARTED, INIT_SIGNAL, TIMED_OUT
    };

    public TetrisController(SimulationController sController, NetworkHandlerAbstract nH, StepGenerator sG) {
        this(sController, nH, sG, true);
    }

    public TetrisController(SimulationController sController, NetworkHandlerAbstract nH, StepGenerator sG, boolean autorun) {
        simulationController = sController;
        networkHandler = nH;
        networkHandler.addObserver(this);
        stepGenerator = sG;
        stepGenerator.addObserver(this);
        replayData = new ReplayData();
        this.autorun = autorun;
    }

    public int getLocalSessionID() {
        return localSessionID;
    }

    public Map<Integer, String> getSessionMap() {
        return sessionMap;
    }

    public SimulationStateAbstract getSimulationStateInterface(int sessionId) {
        return simulationController.getSimulationStateInterface(sessionId);
    }

    public Map<Integer, ? extends SimulationStateAbstract> getSimulationStateInterfaceMap() {
        return simulationController.getGameEngines();
    }

    public InputSampler getInputSampler() {
        return stepGenerator.getInputSampler();
    }

    public Exception getThrownException() {
        return thrownException;
    }

    public void startServer() throws RemoteException, MalformedURLException {
        try {
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        } catch (RemoteException ex) {
            System.out.println("Caught remote exception: " + ex.getMessage());
            handleException(ex);
        }
        Registry registry = LocateRegistry.getRegistry();
        gameServer = new GameServer(TetrisController.SERVER_NAME, registry);
        gameServer.startDiscoveryServer();
    }

    public void connectToServer(String ip, int port, String nickname) {
        networkHandler.connectToServer(ip, TetrisController.SERVER_NAME, nickname);
    }

    public void disconnectFromServer() {
        //TODO delete All Informations from the played Game
        networkHandler.deleteObserver(this);
        stepGenerator.deleteObserver(this);
        networkHandler.disconnectFromServer();
    }

    public void startGame(long blockQueueSeed, int nbrOfJokers, boolean includeSpecialBlocks, int startLevel) {
        gameServer.startGame(blockQueueSeed, nbrOfJokers, includeSpecialBlocks, startLevel);
        gameServer.stopDiscoveryServer();
    }

    @Override
    public void update(Observable o, Object o1) {
        if (o == null || o1 == null) {
            throw new IllegalStateException();
        }

//        updateReplayData(o, o1);

        switch ((UpdateType) o1) {
            case STEP:
                StepProducerInterface producer = (StepProducerInterface) o;
                Step step = producer.getStep();
                if (step == null) {
                    throw new IllegalStateException();
                }
                if (o == stepGenerator) {
                    simulationController.addStep(step);
                    networkHandler.addStep(step);
                } else if (step.getSessionID() != localSessionID) {
                    simulationController.addStep(step);
                }
                break;
            case CONNECTION_ESTABLISHED:
                localSessionID = networkHandler.getLocalSessionInformation().getId();
                sessionMap = networkHandler.getSessionMap();

                setChanged();
                notifyObservers(UpdateType.CONNECTION_ESTABLISHED);
                break;
            case INIT_SIGNAL:
                stepGenerator.setSessionID(localSessionID);
                simulationController.setGameParameters(networkHandler.getGameParams(), sessionMap);

                setChanged();
                notifyObservers(UpdateType.INIT_SIGNAL);
                networkHandler.sendReadySignal();
                break;
            case GAME_STARTED:
                simulationController.initSimulation();
                if (autorun) {
                    gameRunning = true;
                    run();
                }
                break;
            case SESSION_REMOVED:
                if (gameRunning) {
                    simulationController.removeSession(networkHandler.getRemovedSession().getId());
                }
                break;
            case TIMED_OUT:
                abortGame();
                break;
            case EXCEPTION_THROWN:
                handleException(networkHandler.getThrownException());
                break;
        }
    }

    private void updateReplayData(Observable o, Object o1) {
        switch ((UpdateType) o1) {
            case STEP:
                if (o == networkHandler) {
                    this.replayData.addStep(networkHandler.getStep());
                }
                break;
            case INIT_SIGNAL:
                this.replayData.setGameParams(networkHandler.getGameParams());
                break;
            case GAME_STARTED:
                this.replayData.setOwnSessionId(networkHandler.getLocalSessionInformation().getId());
                this.replayData.setSessionList(networkHandler.getSessionMap());
                break;
        }
    }

    public void saveReplayData(String filename) throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(filename);
        ObjectOutputStream out = new ObjectOutputStream(fos);
        out.writeObject(replayData);
        out.close();
    }

    public void abortGame() {
        for (int sessionID : sessionMap.keySet()) {
            simulationController.removeSession(sessionID);
        }
        gameRunning = false;
        networkHandler.deleteObserver(this);
        stepGenerator.deleteObserver(this);
    }

    private void handleException(Exception e) {
        System.out.println(e.getMessage());

        thrownException = e;
        setChanged();
        notifyObservers(UpdateType.EXCEPTION_THROWN);
    }

    /*
     * Called every 50ms
     *
     * public for testing
     */
    public void runStep() {
        try {
            if (currentStep > 0) { //on the first step we don't have all steps available so we wait for the others and don't simulate yet
                networkHandler.processStep();
                simulationController.simulateStep(currentStep - 1); //Simulate previous step
            }
            stepGenerator.processStep();
            currentStep++;
        } catch (IllegalStateException e) {
            System.out.println("Caught illegal state exception: " + e.getMessage());
            handleException(e);
            abortGame();
        }
    }

    //Start the step timer
    private void run() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (gameRunning) {
                    runStep();
                }
            }
        }).start();
    }
}
