/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networkTest;

import domain.Step;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Observable;
import java.util.Observer;
import domain.TetrisController.UpdateType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.SessionInformation;
import network.client.NetworkHandlerImpl;
import network.server.GameServerImpl;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class RMILocalhostTest {

    private GameServerImpl gameServerImpl;
    private final String SERVER_NAME = "Tetris-Server";
    private final String PLAYER_NAME = "TestPlayer";
    private final int MAX_SESSIONS = 4;
    private static Registry registry;
    private static final String IP = "localhost";
    private static boolean flag;
    private static int count;

    public RMILocalhostTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws RemoteException, MalformedURLException {
        gameServerImpl = new GameServerImplWithoutThread(SERVER_NAME, registry);
        flag = false;
        count = 0;
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testUpdateTypeConnectToServer() {
        NetworkHandlerImpl handler = new NetworkHandlerImplWithoutThreads();
        handler.addObserver(new Observer() {

            @Override
            public void update(Observable o, Object o1) {
                if ((UpdateType) o1 == UpdateType.CONNECTION_ESTABLISHED) {
                    flag = true;
                }
            }
        });
        handler.connectToServer(IP, SERVER_NAME, PLAYER_NAME);
        assertTrue(flag);
    }

    @Test
    public void testOwnSessionInformationConnectToServer() {
        final NetworkHandlerImpl handler = new NetworkHandlerImplWithoutThreads();
        handler.addObserver(new Observer() {

            @Override
            public void update(Observable o, Object o1) {
                if (handler.getOwnSession().getNickname() == null ? PLAYER_NAME == null : handler.getOwnSession().getNickname().equals(PLAYER_NAME)) {
                    flag = true;
                }
            }
        });
        handler.connectToServer(IP, SERVER_NAME, PLAYER_NAME);
        assertTrue(flag);
    }

    @Test
    public void testSessionListConnectToServer() {
        final NetworkHandlerImpl handler = new NetworkHandlerImplWithoutThreads();
        handler.addObserver(new Observer() {

            @Override
            public void update(Observable o, Object o1) {
                if (!handler.getSessionList().isEmpty()) {
                    flag = true;
                }
            }
        });
        handler.connectToServer(IP, SERVER_NAME, PLAYER_NAME);
        assertTrue(flag);
    }

    @Test
    public void testServerFullException() throws RemoteException {
        for (int i = 0; i < 4; i++) {
            gameServerImpl.createSession("bla" + i, new ClientRemoteAdapter());
        }
        final NetworkHandlerImpl handler = new NetworkHandlerImplWithoutThreads();
        handler.addObserver(new Observer() {

            @Override
            public void update(Observable o, Object o1) {
                if ((UpdateType) o1 == UpdateType.EXCEPTION_THROWN) {
//               if (handler.getThrownException().getMessage().equals(new ServerFullException().getMessage())) {
                    flag = true;
//               }
                }
            }
        });
        handler.connectToServer(IP, SERVER_NAME, PLAYER_NAME);
        assertTrue(flag);
    }

    @Test
    public void testSessionAddedUpdate() throws RemoteException {
        final NetworkHandlerImpl handler = new NetworkHandlerImplWithoutThreads();
        handler.addObserver(new Observer() {

            @Override
            public void update(Observable o, Object o1) {
                if ((UpdateType) o1 == UpdateType.SESSION_ADDED) {
                    if (handler.getAddedSession().getNickname().equals("bla")) {
                        flag = true;
                    }
                }
            }
        });
        handler.connectToServer(IP, SERVER_NAME, PLAYER_NAME);
        gameServerImpl.createSession("bla", new ClientRemoteAdapter());
        assertTrue(flag);
    }

    @Test
    public void testMultipleConnections() {
        Observer observer = new Observer() {

            @Override
            public void update(Observable o, Object o1) {
                if ((UpdateType) o1 == UpdateType.CONNECTION_ESTABLISHED) {
                    count++;
                }
            }
        };
        for (int i = 0; i < 4; i++) {
            NetworkHandlerImpl handler = new NetworkHandlerImplWithoutThreads();
            handler.addObserver(observer);
            handler.connectToServer(IP, SERVER_NAME, PLAYER_NAME);
        }
        assertEquals(4, count);
    }

    public void testServerFull() {
        Observer observer = new Observer() {

            @Override
            public void update(Observable o, Object o1) {
                if ((UpdateType) o1 == UpdateType.CONNECTION_ESTABLISHED) {
                    count++;
                }
            }
        };
        for (int i = 0; i < 5; i++) {
            NetworkHandlerImpl handler = new NetworkHandlerImplWithoutThreads();
            handler.addObserver(observer);
            handler.connectToServer(IP, SERVER_NAME, PLAYER_NAME);
        }
        assertEquals(4, count);
    }

    @Test
    public void testDisconnect() {
        NetworkHandlerImpl[] handlers = new NetworkHandlerImpl[4];
        Observer observer = new Observer() {

            @Override
            public void update(Observable o, Object o1) {
                if ((UpdateType) o1 == UpdateType.SESSION_REMOVED) {
                    count++;
                }
            }
        };
        for (int i = 0; i < handlers.length; i++) {
            NetworkHandlerImpl handler = new NetworkHandlerImplWithoutThreads();
            handlers[i] = handler;
            handler.addObserver(observer);
            handler.connectToServer(IP, SERVER_NAME, PLAYER_NAME);
        }
        handlers[0].disconnectFromServer();
        assertEquals(3, count);
    }

    @Test
    public void testSendChatMessage() {
        final String MESSAGE = "Hello";
        final String SENDER = "Sender";
        Observer observer = new Observer() {

            @Override
            public void update(Observable o, Object o1) {
                if ((UpdateType) o1 == UpdateType.CHAT_MESSAGE_RECEIVED) {
                    NetworkHandlerImpl handler = (NetworkHandlerImpl) o;
                    if (handler.getChatMessage().getMessage().equals(MESSAGE)) {
                        if (handler.getChatMessage().getSender().getNickname().equals(SENDER)) {
                            count++;
                        }
                    }
                }
            }
        };
        NetworkHandlerImpl sender = new NetworkHandlerImplWithoutThreads();
        sender.addObserver(observer);
        sender.connectToServer(IP, SERVER_NAME, SENDER);
        for (int i = 0; i < MAX_SESSIONS - 1; i++) {
            NetworkHandlerImpl handler = new NetworkHandlerImplWithoutThreads();
            handler.addObserver(observer);
            handler.connectToServer(IP, SERVER_NAME, PLAYER_NAME + " 1");
        }
        sender.sendChatMessage(MESSAGE);
        assertEquals(4, count);
    }

    @Test
    public void testStartSignal() {
        Observer observer = new Observer() {

            @Override
            public void update(Observable o, Object o1) {
                if ((UpdateType) o1 == UpdateType.GAME_STARTED) {
                    count++;
                }
            }
        };
        for (int i = 0; i < MAX_SESSIONS; i++) {
            NetworkHandlerImpl handler = new NetworkHandlerImplWithoutThreads();
            handler.addObserver(observer);
            handler.connectToServer(IP, SERVER_NAME, PLAYER_NAME + i);
        }
        gameServerImpl.startGame();
        assertEquals(MAX_SESSIONS, count);
    }

    @Test
    public void testStepReceived() {
        final String SENDER = "Sender";
        List<NetworkHandlerImpl> otherPlayers = new ArrayList<NetworkHandlerImpl>();
        Observer observer = new Observer() {

            @Override
            public void update(Observable o, Object o1) {
                if ((UpdateType) o1 == UpdateType.STEP) {
                    count++;
                }
            }
        };
        NetworkHandlerImpl sender = new NetworkHandlerImplWithoutThreads();
        sender.addObserver(observer);
        sender.connectToServer(IP, SERVER_NAME, SENDER);
        for (int i = 0; i < MAX_SESSIONS - 1; i++) {
            NetworkHandlerImpl handler = new NetworkHandlerImplWithoutThreads();
            otherPlayers.add(handler);
            handler.addObserver(observer);
            handler.connectToServer(IP, SERVER_NAME, PLAYER_NAME + " 1");
        }
        gameServerImpl.startGame();
        sender.addStep(new Step(1, 3));

        for (NetworkHandlerImpl nh : otherPlayers) {
            nh.niggasInParis();
        }
        assertEquals(3, count);
    }

    @Test
    public void testSerialStep() {
        final String P1_NAME = "Caesar";
        final String P2_NAME = "Brutus";
        final NetworkHandlerImpl p1 = new NetworkHandlerImplWithoutThreads();
        p1.connectToServer(IP, SERVER_NAME, P1_NAME);
        final SessionInformation p1Info = p1.getOwnSession();

        final NetworkHandlerImpl p2 = new NetworkHandlerImplWithoutThreads();
        p2.connectToServer(IP, SERVER_NAME, P2_NAME);
        final SessionInformation p2Info = p2.getOwnSession();
        final int NBR_OF_STEPS = 50;

        Observer observer = new Observer() {

            int sequNr = 0;

            @Override
            public void update(Observable o, Object o1) {
                if ((UpdateType) o1 == UpdateType.STEP) {
                    Step step = p1.getStep();
                    assertEquals(step.getSequenceNumber(), sequNr++);
                    assertEquals(step.getSessionID(), p2Info.getId());
                }
            }
        };

        Observer p2_observer = new Observer() {

            int sequNr = 0;

            @Override
            public void update(Observable o, Object o1) {
                if ((UpdateType) o1 == UpdateType.STEP) {
                    Step step = p2.getStep();
                    assertEquals(step.getSequenceNumber(), sequNr++);
                    assertEquals(step.getSessionID(), p1Info.getId());
                }
            }
        };

        p1.addObserver(observer);

        p2.addObserver(p2_observer);

        gameServerImpl.startGame();

        for (int i = 0; i < NBR_OF_STEPS; i++) {
            p1.addStep(new Step(i, p1Info.getId()));
            p2.addStep(new Step(i, p2Info.getId()));
        }
        p1.niggasInParis();
        p2.niggasInParis();
    }

    @Test
    public void testStepDuration() {
        final String SENDER = "Sender";
        List<NetworkHandlerImpl> otherPlayers = new ArrayList<NetworkHandlerImpl>();

        NetworkHandlerImpl sender = new NetworkHandlerImplWithoutThreads();
        sender.connectToServer(IP, SERVER_NAME, SENDER);
        for (int i = 0; i < MAX_SESSIONS - 1; i++) {
            NetworkHandlerImpl handler = new NetworkHandlerImplWithoutThreads();
            otherPlayers.add(handler);
            handler.connectToServer(IP, SERVER_NAME, PLAYER_NAME + " 1");
        }

        gameServerImpl.startGame();

        long timeBefore = System.currentTimeMillis();
        sender.addStep(new Step(1, 3));
        long timeAfter = System.currentTimeMillis();

        System.out.println(timeAfter - timeBefore);
        assertTrue(timeAfter - timeBefore < 50);
    }

    @Test
    public void testStepDurationWithNetworkDelay() {
        final String SENDER = "Sender";
        List<NetworkHandlerImpl> otherPlayers = new ArrayList<NetworkHandlerImpl>();

        NetworkHandlerImpl sender = new NetworkHandlerImplWithoutThreads();
        sender.connectToServer(IP, SERVER_NAME, SENDER);
        for (int i = 0; i < MAX_SESSIONS - 1; i++) {
            NetworkHandlerImpl handler = new NetworkHandlerImplWithoutThreads(){

                //Sleep of 30 ms for faking network-Delay
                @Override
                public void notifyStepReceived(Step step) {
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(RMILocalhostTest.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    super.notifyStepReceived(step);
                }
                
            };
            otherPlayers.add(handler);
            handler.connectToServer(IP, SERVER_NAME, PLAYER_NAME + " 1");
        }

        gameServerImpl.startGame();

        long timeBefore = System.currentTimeMillis();
        sender.addStep(new Step(1, 3));
        long timeAfter = System.currentTimeMillis();

        System.out.println(timeAfter - timeBefore);
        assertTrue(timeAfter - timeBefore < 50);
    }
}
