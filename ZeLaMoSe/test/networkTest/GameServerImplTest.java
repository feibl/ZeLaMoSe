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
   private static boolean notificationSuccessful = false;

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
      notificationSuccessful = false;
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
      Observer observer = new Observer() {
         @Override
         public void update(Observable o, Object o1) {
            assertEquals(hostNetworkHandler.getAddedSession().getNickname(), PLAYER_NAME);
         }
      };
      
      hostNetworkHandler.addObserver(observer);
                  
      gameServerImpl.createSession("Host", new LobbyHandler(hostNetworkHandler));
      gameServerImpl.createSession(PLAYER_NAME, new LobbyHandler(new NetworkHandlerImpl()));     
   }
   
   @Test
   public void testSessionAddedNoNotificationForAccessingPlayer() throws RemoteException {
      final NetworkHandlerImpl newPlayerNetworkHandler = new NetworkHandlerImpl();

      Observer observer = new Observer() {
         @Override
         public void update(Observable o, Object o1) {
            notificationSuccessful = true;
         }
      };
      
      newPlayerNetworkHandler.addObserver(observer);
                  
      gameServerImpl.createSession("Host", new LobbyHandler(new NetworkHandlerImpl()));
      gameServerImpl.createSession(PLAYER_NAME, new LobbyHandler(new NetworkHandlerImpl()));
      assertFalse(notificationSuccessful);
   }

   @Test(expected = ServerFullException.class)
   public void testCreateSessionServerFull() throws RemoteException {
      for (int i = 0; i < 5; i++) {
         gameServerImpl.createSession("Player", new LobbyHandler(new NetworkHandlerImpl()));
      }
   }

   @Test
   public void testCreatMultipleSession() throws RemoteException {
      HashSet<Integer> ids = new HashSet<Integer>();
      for (int i = 0; i < 4; i++) {
         ids.add(gameServerImpl.createSession("Player", new LobbyHandler(new NetworkHandlerImpl())).getId());
      }
      assertEquals(4, ids.size());
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
      final NetworkHandlerImpl newPlayerNetworkHandler = new NetworkHandlerImpl();

      Observer observer = new Observer() {
         @Override
         public void update(Observable o, Object o1) {
            assertEquals(newPlayerNetworkHandler.getRemovedSession().getNickname(), "Host");
         }
      };     
      newPlayerNetworkHandler.addObserver(observer);           
      gameServerImpl.createSession("Host", new ClientRemoteAdapter(){

         @Override
         public void notifySessionAdded(SessionInformation session) throws RemoteException {
            throw new RemoteException();
         }
         
      });
      gameServerImpl.createSession(PLAYER_NAME, new LobbyHandler(new NetworkHandlerImpl()));
   }
}