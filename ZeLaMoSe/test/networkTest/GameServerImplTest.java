/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networkTest;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.*;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class GameServerImplTest {

   GameServerImpl gameServerImpl;
   private final String SERVER_NAME = "Tetris-Server";
   private final String PLAYER_NAME = "TestPlayer";
   private static Registry registry;
   private static boolean flag;
   private static int count;

   @BeforeClass
   public static void setUpClass() throws Exception {
      registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
   }

   @AfterClass
   public static void tearDownClass() throws Exception {
   }

   @Before
   public void setUp() throws RemoteException, MalformedURLException {
      gameServerImpl = new GameServerImpl(SERVER_NAME, registry);
      flag = false;
      count = 0;
   }

   @After
   public void tearDown() {
   }

   @Test
   public void testCreateSession() throws RemoteException {
      SessionInformation sessionInfo = gameServerImpl.createSession(PLAYER_NAME, new LobbyHandler(new NetworkHandlerImpl()));
      assertArrayEquals(new Object[]{PLAYER_NAME, 1}, new Object[]{sessionInfo.getNickname(), sessionInfo.getId()});
   }

   @Test
   public void testSessionAddedNotification() throws RemoteException {
      final NetworkHandlerImpl hostNetworkHandler = new NetworkHandlerImpl();
      hostNetworkHandler.addObserver(new Observer() {

         @Override
         public void update(Observable o, Object o1) {
            assertEquals(hostNetworkHandler.getAddedSession().getNickname(), PLAYER_NAME);
         }
      });

      gameServerImpl.createSession("Host", new LobbyHandler(hostNetworkHandler));
      gameServerImpl.createSession(PLAYER_NAME, new LobbyHandler(new NetworkHandlerImpl()));
   }

   @Test
   public void testSessionAddedNoNotificationForAccessingPlayer() throws RemoteException {
      final NetworkHandlerImpl newPlayerNetworkHandler = new NetworkHandlerImpl();

      Observer observer = new Observer() {

         @Override
         public void update(Observable o, Object o1) {
            flag = true;
         }
      };

      newPlayerNetworkHandler.addObserver(observer);

      gameServerImpl.createSession("Host", new LobbyHandler(new NetworkHandlerImpl()));
      gameServerImpl.createSession(PLAYER_NAME, new LobbyHandler(new NetworkHandlerImpl()));
      assertFalse(flag);
   }

   @Test(expected = ServerFullException.class)
   public void testCreateSessionServerFull() throws RemoteException {
      for (int i = 0; i < 5; i++) {
         gameServerImpl.createSession("Player", new LobbyHandler(new NetworkHandlerImpl()));
      }
   }

   @Test
   public void testCreateMultipleSession() throws RemoteException {
      final HashSet<Integer> notificatedLogins = new HashSet<Integer>();
      ClientRemoteAdapter clientRemoteAdapter = new ClientRemoteAdapter() {

         @Override
         public void notifySessionAdded(SessionInformation session) throws RemoteException {
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
         sessions[i] = gameServerImpl.createSession("Player", new LobbyHandler(new NetworkHandlerImpl()));
      }
      List<SessionInformation> createdSessions = gameServerImpl.getSessionList();
      assertArrayEquals(sessions, createdSessions.toArray());
   }

   @Test
   public void testRemoteExceptionWhileSessionAddedNotification() throws RemoteException {
      gameServerImpl.createSession(PLAYER_NAME, new ClientRemoteAdapter() {

         @Override
         public void notifySessionAdded(SessionInformation session) throws RemoteException {
            throw new RemoteException();
         }
      });
      gameServerImpl.createSession("bla", new ClientRemoteAdapter() {

         @Override
         public void notifySessionRemoved(SessionInformation session) throws RemoteException {
            assertTrue(false);
         }
      });
   }

   @Test(expected = RemoteException.class)
   public void testRemoteExceptionWhileReportingRemote() throws RemoteException {
      gameServerImpl.createSession(PLAYER_NAME, new ClientRemoteAdapter() {

         @Override
         public void reportServerRemote(ServerRemote remote) throws RemoteException {
            throw new RemoteException();
         }
      });
   }

   @Test
   public void testSessionListRemoteExceptionWhileReportingRemote() throws RemoteException {
      gameServerImpl.createSession(SERVER_NAME, new ClientRemoteAdapter());
      try {
         gameServerImpl.createSession("newPlayer", new ClientRemoteAdapter() {

            @Override
            public void reportServerRemote(ServerRemote remote) throws RemoteException {
               throw new RemoteException();
            }
         });
      } catch (RemoteException ex) {
         Logger.getLogger(GameServerImplTest.class.getName()).log(Level.SEVERE, null, ex);
      }
      assertTrue(gameServerImpl.getSessionList().size() == 1);
   }

   @Test
   public void testNoSessionRemovedMessageForAccessingPlayer() throws RemoteException {
      gameServerImpl.createSession(PLAYER_NAME, new ClientRemoteAdapter());
      try {
         gameServerImpl.createSession("newPlayer", new ClientRemoteAdapter() {

            @Override
            public void reportServerRemote(ServerRemote remote) throws RemoteException {
               throw new RemoteException();
            }
         });
      } catch (RemoteException ex) {
         Logger.getLogger(GameServerImplTest.class.getName()).log(Level.SEVERE, null, ex);
      }
      assertTrue(gameServerImpl.getSessionList().size() == 1);
   }

   @Test
   public void testPostChatMessage() throws RemoteException {
      final String MESSAGE = "Hallo";
      final int ID = 5;
      ClientRemoteAdapter fakeRemote = new ClientRemoteAdapter() {

         @Override
         public void notifyChatMessage(ChatMessage message) throws RemoteException {
            if (message.getMessage().equals(MESSAGE)) {
               if (message.getSender().getId() == ID) {
                  count++;
               }
            }
         }
      };
      for (int i = 0; i < 4; i++) {
         gameServerImpl.createSession(SERVER_NAME, fakeRemote);
      }
      gameServerImpl.postMessage(new LobbySession(new SessionInformation(ID, PLAYER_NAME), fakeRemote, gameServerImpl), MESSAGE);
      assertEquals(4, count);
   }
}