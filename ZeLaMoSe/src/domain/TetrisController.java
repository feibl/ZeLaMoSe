/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.interfaces.SimulationStateInterface;
import domain.interfaces.StepProducerInterface;
import java.net.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public boolean autorun = true;
    private Timer timer;
    private SimulationController simulationController;
    private NetworkHandler networkHandler;
    private StepGenerator stepGenerator;
    private int currentStep = 0;
    private final int stepDuration = 50; //in millisecond   
    private GameServer gameServer;
    private String serverIP = "";
    private ConcurrentHashMap<Integer, String> sessionMap;
    private int localSessionID = -1;

    public Object getThrownException() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public String getServerIP() {
        return serverIP;
    }

    public enum UpdateType {

        STEP, SESSION_ADDED, SESSION_REMOVED, CONNECTION_ESTABLISHED, EXCEPTION_THROWN, CHAT_MESSAGE_RECEIVED, GAME_STARTED, INIT_SIGNAL
    };

    public TetrisController(SimulationController sController, NetworkHandler nH, StepGenerator sG) {
        simulationController = sController;
        networkHandler = nH;
        networkHandler.addObserver(this);
        stepGenerator = sG;
        stepGenerator.addObserver(this);
        timer = new Timer();
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

    private String getLocalIP() {
        Enumeration<NetworkInterface> niList;
        try {
            niList = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface iface : Collections.list(niList)) {
                if (!iface.isLoopback() && iface.isUp()) {
                    for (InetAddress addr : Collections.list(iface.getInetAddresses())) {
                        if (addr instanceof Inet4Address) {
                            return addr.getHostAddress();
                        }
                    }
                }
            }
        } catch (SocketException ex) {
        }

        return "localhost";
    }

    public void startServer() throws RemoteException, MalformedURLException {
        try {
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        } catch (RemoteException ex) {
        }
        Registry registry = LocateRegistry.getRegistry();
        gameServer = new GameServerImpl(TetrisController.SERVER_NAME, registry);

        serverIP = getLocalIP();
    }

    public void connectToServer(String ip, int port, String nickname) {
        networkHandler.connectToServer(ip, TetrisController.SERVER_NAME, nickname);

        serverIP = ip;
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
        if (o == null || o1 == null) {
            throw new IllegalStateException();
        }
        switch ((UpdateType) o1) {
            case STEP:
                StepProducerInterface producer = (StepProducerInterface) o;
                Step step = producer.getStep();
                if (step == null) {
                    throw new IllegalStateException();
                }
                simulationController.addStep(step);
                if (step.getSessionID() == localSessionID && o == stepGenerator) {
                    networkHandler.addStep(step);
                }
                break;
            case CONNECTION_ESTABLISHED:
                localSessionID = networkHandler.getOwnSession().getId();
                sessionMap = networkHandler.getSessionList();

                setChanged();
                notifyObservers(UpdateType.CONNECTION_ESTABLISHED);
                break;
            case INIT_SIGNAL:
                stepGenerator.setSessionID(localSessionID);
                long seed = networkHandler.getBlockQueueSeed();
                for (Map.Entry<Integer, String> entry : sessionMap.entrySet()) {
                    simulationController.addSession(entry.getKey(), entry.getValue(), new GameEngine(entry.getKey(), seed));
                }

                setChanged();
                notifyObservers(UpdateType.INIT_SIGNAL);
                networkHandler.sendReadySignal();

                break;
            case GAME_STARTED:
                simulationController.initSimulation();
                if (autorun) {
                    run();
                }
                break;
        }
    }

    /*
     * Called every 50ms
     *
     * public for testing
     */
    public void runStep() {
        while (true) {
            System.out.println("running step: " + currentStep + " time: " + System.currentTimeMillis());
            if (currentStep > 0) { //on the first step we don't have all steps available so we wait for the others and don't simulate yet
                networkHandler.niggasInParis();
                simulationController.simulateStep(currentStep - 1); //Simulate previous step
            }
            stepGenerator.niggasInParis();
            currentStep++;
        }
    }

    //Start the step timer
    private void run() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                runStep();
            }
        }).start();
    }
}
