/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networkTest;

import domain.TetrisController;
import java.net.MalformedURLException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import network.ChatMessage;
import network.ServerFullException;
import network.SessionInformation;
import network.client.Handler;
import network.client.NetworkHandler;
import network.server.GameServer;
import network.server.Session;
import static org.junit.Assert.*;
import org.junit.*;
import java.io.*;
import network.server.SessionRemoteInterface;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class GameServerImplTest {

    GameServer gameServerImpl;
    private final String SERVER_NAME = "Tetris-Server";
    private final String PLAYER_NAME = "TestPlayer";
    private static boolean flag;
    private static int count1;
    private static int count2;

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws RemoteException, MalformedURLException {
        gameServerImpl = new GameServer();
        flag = false;
        count1 = 0;
        count2 = 0;
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCreateSession() throws RemoteException {
        SessionInformation sessionInfo = gameServerImpl.createSession(PLAYER_NAME, new Handler(new NetworkHandler())).getRemoteSessionInformation();
        assertEquals(new SessionInformation(1, PLAYER_NAME), sessionInfo);
    }

    @Test
    public void testSessionAddedNotification() throws RemoteException {
        final NetworkHandler hostNetworkHandler = new NetworkHandler();
        hostNetworkHandler.addObserver(new Observer() {

            @Override
            public void update(Observable o, Object o1) {
                if (o1 == TetrisController.UpdateType.SESSION_ADDED) {
                    flag = true;
                }
            }
        });

        gameServerImpl.createSession("Host", new Handler(hostNetworkHandler));
        gameServerImpl.createSession(PLAYER_NAME, new Handler(new NetworkHandler()));
        assertTrue(flag);
    }

    @Test
    public void testSessionAddedNoNotificationForAccessingPlayer() throws RemoteException {
        final NetworkHandler newPlayerNetworkHandler = new NetworkHandler();

        Observer observer = new Observer() {

            @Override
            public void update(Observable o, Object o1) {
                if (o1 == TetrisController.UpdateType.SESSION_ADDED) {
                    flag = true;
                }
            }
        };

        newPlayerNetworkHandler.addObserver(observer);
        gameServerImpl.createSession(PLAYER_NAME, new Handler(newPlayerNetworkHandler));

        assertFalse(flag);
    }

    @Test(expected = ServerFullException.class)
    public void testCreateSessionServerFull() throws RemoteException {
        for (int i = 0; i < 5; i++) {
            gameServerImpl.createSession("Player", new Handler(new NetworkHandler()));
        }
    }

    @Test
    public void testCreateMultipleSession() throws RemoteException {
        final HashSet<Integer> notificatedLogins = new HashSet<Integer>();
        ClientRemoteAdapter clientRemoteAdapter = new ClientRemoteAdapter() {

            @Override
            public void receiveSessionAddedMessage(SessionInformation session) throws RemoteException {
                notificatedLogins.add(session.getId());
            }
        };
        for (int i = 0; i < 4; i++) {
            gameServerImpl.createSession(Integer.toString(i), clientRemoteAdapter);
        }
        assertEquals(3, notificatedLogins.size());
    }

    @Test
    public void testGetSessionList() throws RemoteException {
        final int NBR_OF_PLAYERS = 4;
        SessionInformation[] sessions = new SessionInformation[NBR_OF_PLAYERS];
        for (int i = 0; i < NBR_OF_PLAYERS; i++) {
            sessions[i] = gameServerImpl.createSession("Player", new Handler(new NetworkHandler())).getRemoteSessionInformation();
        }
        List<SessionInformation> createdSessions = gameServerImpl.getSessionList();
        assertArrayEquals(sessions, createdSessions.toArray());
    }

    @Test
    public void testDeleteUnreachableSession() throws RemoteException {
        gameServerImpl.createSession(PLAYER_NAME, new ClientRemoteAdapter() {

            @Override
            public void receiveSessionAddedMessage(SessionInformation session) throws RemoteException {
                throw new RemoteException();
            }
        });

        gameServerImpl.createSession("bla", new Handler(new NetworkHandler()));
        assertEquals(1, gameServerImpl.getSessionList().size());
    }

    @Test
    public void testExceptionWhileSessionRemovedNotification() throws RemoteException {
        gameServerImpl.createSession(PLAYER_NAME, new ClientRemoteAdapter() {

            @Override
            public void receiveSessionRemovedMessage(SessionInformation session) throws RemoteException {
                throw new RemoteException();
            }
        });
        gameServerImpl.createSession(PLAYER_NAME, new ClientRemoteAdapter() {

            @Override
            public void receiveSessionAddedMessage(SessionInformation session) throws RemoteException {
                throw new RemoteException();
            }
        });
        gameServerImpl.createSession("bla", new Handler(new NetworkHandler()));
        assertEquals(1, gameServerImpl.getSessionList().size());
    }
    
    @Test
    public void testRemoveSession() throws RemoteException {
        SessionRemoteInterface session = gameServerImpl.createSession(PLAYER_NAME, new Handler(new NetworkHandler()));
        for(int i = 0; i < 3; i++) {
            gameServerImpl.createSession("Player" + i, new Handler(new NetworkHandler()));
        }
        session.disconnect();
        assertEquals(3, gameServerImpl.getSessionList().size());
    }

    @Test
    public void testPostChatMessage() throws RemoteException {
        final String MESSAGE = "Hallo";
        final int ID = 5;
        ClientRemoteAdapter fakeRemote = new ClientRemoteAdapter() {

            @Override
            public void receiveChatMessage(ChatMessage message) throws RemoteException {
                if (message.getMessage().equals(MESSAGE)) {
                    if (message.getSender().getId() == ID) {
                        count1++;
                    }
                }
            }
        };
        for (int i = 0; i < 4; i++) {
            gameServerImpl.createSession(SERVER_NAME, fakeRemote);
        }
        gameServerImpl.postChatMessage(new Session(new SessionInformation(ID, PLAYER_NAME), fakeRemote, gameServerImpl), MESSAGE);
        assertEquals(4, count1);
    }
}
