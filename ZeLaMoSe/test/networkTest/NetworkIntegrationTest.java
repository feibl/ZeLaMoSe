/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networkTest;

import domain.Config;
import domain.Step;
import domain.TetrisController;
import java.io.File;
import java.net.MalformedURLException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.client.NetworkHandlerAbstract;
import network.client.NetworkHandler;
import network.server.GameServer;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Christian Mollekopf <cmolleko@hsr.ch>
 */
public class NetworkIntegrationTest {

    private GameServer gameServerImpl;
    private final String SERVER_NAME = "Tetris-Server";
    private final String PLAYER_NAME = "TestPlayer";
    private final int MAX_SESSIONS = 4;
    private static Registry registry;
    private static final String IP = "localhost";
    private static boolean flag;
    private static int count;
    private ExecutorService executor = Executors.newFixedThreadPool(5);

    @BeforeClass
    public static void setUpClass() throws Exception {
        File policy = Config.convertRMI(GameServer.class);
        System.setProperty("java.security.policy", policy.getAbsolutePath());
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }
        registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws RemoteException, MalformedURLException {
        gameServerImpl = new GameServer(SERVER_NAME, registry);
        flag = false;
        count = 0;
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testConnectOneSession() {
        connectSessions(1);
        waitForConnectionEstablished();
        assertEquals(1, gameServerImpl.getSessionList().size());
    }

    @Test
    public void testZeroSessionsAtStart() {
        assertEquals(0, gameServerImpl.getSessionList().size());
    }

    private List<NetworkHandler> connectSessions(int nbrOfSessions) {
        List<NetworkHandler> handlers = new ArrayList<NetworkHandler>();

        for (int i = 0; i < nbrOfSessions; i++) {
            NetworkHandler handler = new NetworkHandler();
            handlers.add(handler);
            handler.connectToServer(IP, SERVER_NAME, PLAYER_NAME + i);
        }
        return handlers;
    }

    @Test
    public void testConnectMaxSessions() {
        connectSessions(MAX_SESSIONS);
        waitForConnectionEstablished();

        assertEquals(MAX_SESSIONS, gameServerImpl.getSessionList().size());
    }

    private void waitForConnectionEstablished() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(NetworkIntegrationTest.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (gameServerImpl.getSessionList().size() == MAX_SESSIONS) {
                break;
            }
        }
    }

    @Test
    public void testStepDuration() {
        final List<NetworkHandler> players = connectSessions(MAX_SESSIONS);
        waitForConnectionEstablished();
        gameServerImpl.startGame();

        class MyCallable implements Callable<Long>, Observer {

            private long t = -1;

            @Override
            public void update(Observable o, Object o1) {
                if (o1 == TetrisController.UpdateType.STEP) {
                    count++;
                    t = System.currentTimeMillis();
                }
            }

            @Override
            public Long call() {
                for (NetworkHandlerAbstract handler : players) {
                    handler.processStep();
                }
                return new Long(t);
            }
        };

        MyCallable myCallable = new MyCallable();
        for (NetworkHandlerAbstract handler : players) {
            handler.addObserver(myCallable);
        }

        FutureTask<Long> future = new FutureTask<Long>(myCallable);
        executor.execute(future);

        long timeBefore = System.currentTimeMillis();
        for (NetworkHandler handler : players) {
            handler.addStep(new Step(0, handler.getOwnSession().getId()));
        }
        gameServerImpl.distributeSteps();

        long timeAfter = -1;

        try {
            timeAfter = future.get(500, TimeUnit.MILLISECONDS).longValue();
        } catch (InterruptedException ex) {
            Logger.getLogger(NetworkIntegrationTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(NetworkIntegrationTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TimeoutException ex) {
            future.cancel(true);
        }

        assertEquals(MAX_SESSIONS * MAX_SESSIONS, count);
        assertTrue(timeAfter >= 0);
        System.out.println(timeAfter - timeBefore);
        assertTrue(timeAfter - timeBefore < 50);
    }
}
