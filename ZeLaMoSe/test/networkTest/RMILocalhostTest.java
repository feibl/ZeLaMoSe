package networkTest;

import domain.Config;
import domain.Step;
import domain.TetrisController.UpdateType;
import java.io.File;
import java.net.MalformedURLException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
import network.GameAlreadyStartedException;
import network.SessionInformation;
import network.client.Handler;
import network.client.NetworkHandler;
import network.server.GameServer;
import network.server.Session;
import static org.junit.Assert.*;
import org.junit.*;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class RMILocalhostTest {

    private GameServerWithoutThread gameServerImpl;
    private final String SERVER_NAME = "Tetris-Server";
    private final String PLAYER_NAME = "TestPlayer";
    private final int MAX_SESSIONS = GameServer.MAX_SESSIONS;
    private static Registry registry;
    private static final String IP = "localhost";
    private static boolean flag;
    private static int count;

    public RMILocalhostTest() {
    }

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
        gameServerImpl = new GameServerWithoutThread(SERVER_NAME, registry);
        flag = false;
        count = 0;
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testUpdateTypeConnectToServer() {
        Observer observer = createCountObserver(UpdateType.CONNECTION_ESTABLISHED);
        connectSessions(1, observer);
        assertEquals(1, count);
    }

    @Test
    public void testOwnSessionInformationConnectToServer() {
        final NetworkHandler handler = new NetworkHandlerWithoutThreads();
        handler.connectToServer(IP, SERVER_NAME, PLAYER_NAME);
        assertEquals(PLAYER_NAME, handler.getLocalSessionInformation().getNickname());
    }

    @Test
    public void testSessionListConnectToServer() {
        Observer observer = createCountObserver(UpdateType.CONNECTION_ESTABLISHED);
        List<NetworkHandler> handlers = connectSessions(MAX_SESSIONS, observer);
        assertEquals(MAX_SESSIONS, handlers.get(0).getSessionMap().size());
    }

    @Test
    public void testServerFullException() throws RemoteException {
        Observer observer = createCountObserver(UpdateType.EXCEPTION_THROWN);
        connectSessions(MAX_SESSIONS + 1, observer);
        assertEquals(1, count);
    }

    @Test
    public void testSessionAddedUpdates() throws RemoteException {
        Observer observer = createCountObserver(UpdateType.SESSION_ADDED);
        connectSessions(1, observer);
        connectSessions(MAX_SESSIONS);
        assertEquals(MAX_SESSIONS - 1, count);
    }

    @Test
    public void testMultipleConnections() {
        Observer observer = createCountObserver(UpdateType.CONNECTION_ESTABLISHED);
        connectSessions(MAX_SESSIONS, observer);
        assertEquals(MAX_SESSIONS, count);
    }

    public void testMaxNumberOfPlayers() {
        Observer observer = createCountObserver(UpdateType.CONNECTION_ESTABLISHED);
        connectSessions(MAX_SESSIONS + 1, observer);
        assertEquals(MAX_SESSIONS, count);
    }

    @Test
    public void testDisconnect() {
        Observer observer = createCountObserver(UpdateType.SESSION_REMOVED);
        List<NetworkHandler> handlers = connectSessions(MAX_SESSIONS, observer);
        handlers.get(0).disconnectFromServer();
        assertEquals(MAX_SESSIONS - 1, count);
    }

    @Test
    public void testDisconnectUnknownSession() throws RemoteException {
        Observer observer = createCountObserver(UpdateType.SESSION_REMOVED);
        connectSessions(MAX_SESSIONS, observer);
        gameServerImpl.removeSession(new Session(new SessionInformation(-1, "Anonymous"), null, gameServerImpl));
        assertEquals(0, count);
    }

    @Test
    public void testSendChatMessage() {
        final String MESSAGE = "hallo";
        Observer observer = createCountObserver(UpdateType.CHAT_MESSAGE_RECEIVED);
        List<NetworkHandler> handlers = connectSessions(MAX_SESSIONS, observer);
        handlers.get(0).sendChatMessage(MESSAGE);
        assertEquals(MAX_SESSIONS, count);
    }

    @Test(expected = GameAlreadyStartedException.class)
    public void testGameAlreadyStartedException() throws RemoteException {
        gameServerImpl.createSession("Hans", new ClientRemoteAdapter());
        gameServerImpl.startGame(1, 0, true, 1);
        gameServerImpl.createSession("Heiri", new ClientRemoteAdapter());
    }

    @Test
    public void testInitSignal() {
        Observer observer = createCountObserver(UpdateType.INIT_SIGNAL);
        connectSessions(MAX_SESSIONS, observer);
        gameServerImpl.startGame(1, 0, true, 1);
        assertEquals(MAX_SESSIONS, count);
    }

    @Test
    public void testStartSignal() {
        Observer observer = createCountObserver(UpdateType.GAME_STARTED);
        List<NetworkHandler> handlers = connectSessions(MAX_SESSIONS, observer);
        gameServerImpl.startGame(1, 0, true, 1);

        for (NetworkHandler handler : handlers) {
            handler.sendReadySignal();
        }

        assertEquals(MAX_SESSIONS, count);
    }

    private List<NetworkHandler> connectSessions(int nbrOfSessions, Observer observer) {
        List<NetworkHandler> handlers = new ArrayList<NetworkHandler>();

        for (int i = 0; i < nbrOfSessions; i++) {
            NetworkHandler handler = new NetworkHandlerWithoutThreads();
            handlers.add(handler);
            handler.addObserver(observer);
            handler.connectToServer(IP, SERVER_NAME, PLAYER_NAME + i);
        }
        return handlers;
    }

    private List<NetworkHandler> connectSessions(int nbrOfSessions) {
        List<NetworkHandler> handlers = new ArrayList<NetworkHandler>();

        for (int i = 0; i < nbrOfSessions; i++) {
            NetworkHandler handler = new NetworkHandlerWithoutThreads();
            handlers.add(handler);
            handler.connectToServer(IP, SERVER_NAME, PLAYER_NAME + i);
        }
        return handlers;
    }

    private Observer createCountObserver(final UpdateType updateType) {
        Observer observer = new Observer() {

            @Override
            public void update(Observable o, Object o1) {
                if ((UpdateType) o1 == updateType) {
                    count++;
                }
            }
        };
        return observer;
    }

    @Test
    public void testStepReceived() {
        Observer observer = createCountObserver(UpdateType.STEP);
        List<NetworkHandler> handlers = connectSessions(MAX_SESSIONS, observer);
        for (NetworkHandler handler : handlers) {
            handler.addStep(new Step(0, handler.getLocalSessionInformation().getId()));
        }
        gameServerImpl.distributeSteps();
        for (NetworkHandler handler : handlers) {
            handler.processStep();
        }
        assertEquals(MAX_SESSIONS * MAX_SESSIONS, count);
    }

    @Test
    public void testDeleteUnreachableSession() throws RemoteException {
        gameServerImpl.createSession(PLAYER_NAME, new ClientRemoteUnreachable());
        gameServerImpl.createSession("bla", new Handler(new NetworkHandler()));
        assertEquals(1, gameServerImpl.getSessionList().size());
    }

    @Test
    public void testExceptionWhileSessionRemovedNotification() throws RemoteException {
        Observer observer = createCountObserver(UpdateType.SESSION_REMOVED);
        connectSessions(1, observer);
        List<NetworkHandler> handlers = connectSessions(MAX_SESSIONS - 2);
        gameServerImpl.createSession(PLAYER_NAME, new ClientRemoteUnreachable());

        handlers.get(0).disconnectFromServer();
        assertEquals(2, count);
    }

    @Test
    public void testExceptionWhileReceivingChatMessage() throws RemoteException {
        Observer observer = createCountObserver(UpdateType.SESSION_REMOVED);
        List<NetworkHandler> handlers = connectSessions(MAX_SESSIONS - 1, observer);
        gameServerImpl.createSession(PLAYER_NAME, new ClientRemoteUnreachable());

        handlers.get(0).sendChatMessage("hallo");
        assertEquals(MAX_SESSIONS - 1, count);
    }

    @Test
    public void testExceptionWhileReceivingInitSignal() throws RemoteException {
        Observer observer = createCountObserver(UpdateType.SESSION_REMOVED);
        connectSessions(MAX_SESSIONS - 1, observer);
        gameServerImpl.createSession(PLAYER_NAME, new ClientRemoteUnreachable());

        gameServerImpl.startGame(1, 0, true, 1);
        assertEquals(MAX_SESSIONS - 1, count);
    }

    @Test
    public void testExceptionWhileReceivingStartSignal() throws RemoteException {
        Observer observer = createCountObserver(UpdateType.SESSION_REMOVED);
        connectSessions(MAX_SESSIONS - 1, observer);
        gameServerImpl.createSession(PLAYER_NAME, new ClientRemoteUnreachable());

        for (int i = 0; i < MAX_SESSIONS; i++) {
            gameServerImpl.notifyReadySignalReceived(null);
        }
        assertEquals(MAX_SESSIONS - 1, count);
    }

    @Test
    public void testExceptionWhileReceivingStep() throws RemoteException {
        Observer observer = createCountObserver(UpdateType.SESSION_REMOVED);
        List<NetworkHandler> handlers = connectSessions(MAX_SESSIONS - 1, observer);
        Session session = (Session) gameServerImpl.createSession(PLAYER_NAME, new ClientRemoteUnreachable());

        gameServerImpl.addStep(session, new Step(0, session.getSessionInformation().getId()));
        for (NetworkHandler handler : handlers) {
            handler.addStep(new Step(0, handler.getLocalSessionInformation().getId()));
        }
        gameServerImpl.distributeSteps();
        assertEquals(MAX_SESSIONS - 1, count);
    }

    @Test
    public void testSerialStep() {
        final int NBR_OF_STEPS = 50;
        final Map<Integer, Set<Integer>> steps = new HashMap<Integer, Set<Integer>>(50);
        List<NetworkHandler> handlers = connectSessions(MAX_SESSIONS, new Observer() {

            @Override
            public void update(Observable o, Object o1) {
                if (o1 == UpdateType.STEP) {
                    NetworkHandler handler = (NetworkHandler) o;
                    int id = handler.getStep().getSessionID();
                    int sequenceNbr = handler.getStep().getSequenceNumber();
                    if (steps.containsKey(sequenceNbr)) {
                        steps.get(sequenceNbr).add(id);
                    } else {
                        steps.put(sequenceNbr, new HashSet<Integer>());
                        steps.get(sequenceNbr).add(id);
                    }
                }
            }
        });
        for (int i = 0; i < NBR_OF_STEPS; i++) {
            for (NetworkHandler handler : handlers) {
                handler.addStep(new Step(i, handler.getLocalSessionInformation().getId()));
            }
            gameServerImpl.distributeSteps();
            for (NetworkHandler handler : handlers) {
                handler.processStep();
            }
        }
        assertEquals(NBR_OF_STEPS, steps.size());
        for (Set<Integer> sessionIds : steps.values()) {
            assertEquals(MAX_SESSIONS, sessionIds.size());
        }
    }

    @Test
    public void testStepDuration() {
        final String SENDER = "Sender";
        List<NetworkHandler> otherPlayers = new ArrayList<NetworkHandler>();

        NetworkHandler sender = new NetworkHandlerWithoutThreads();
        sender.connectToServer(IP, SERVER_NAME, SENDER);
        for (int i = 0; i < MAX_SESSIONS - 1; i++) {
            NetworkHandler handler = new NetworkHandlerWithoutThreads();
            otherPlayers.add(handler);
            handler.connectToServer(IP, SERVER_NAME, PLAYER_NAME + " 1");
        }

        gameServerImpl.startGame(1, 0, true, 1);

        long timeBefore = System.currentTimeMillis();
        sender.addStep(new Step(1, 3));
        long timeAfter = System.currentTimeMillis();

        System.out.println(timeAfter - timeBefore);
        assertTrue(timeAfter - timeBefore < 50);
    }
}
