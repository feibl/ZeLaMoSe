/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networkTest;

import network.SessionAddedMessage;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentLinkedQueue;
import network.*;
import network.NetworkHandler.UpdateType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import org.junit.*;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class NetworkHandlerImplTest implements Observer{
   UpdateType updateType;

   public NetworkHandlerImplTest() {
   }

   @BeforeClass
   public static void setUpClass() throws Exception {
   }

   @AfterClass
   public static void tearDownClass() throws Exception {
   }

   @Before
   public void setUp() {
      
   }

   @Test
   public void connectToServerTest() throws RemoteException, ServerFullException  {
      NetworkHandler networkHandler = new NetworkHandlerImpl(new MockLobbyHandler(), new MockGameserverImpl());
      SessionInformation player1Session = new SessionInformation(1, "Player 1");
      SessionInformation actual = networkHandler.connectToServer("Player 1");
      assertEquals(player1Session.getId(), actual.getId());
      assertEquals(player1Session.getNickname(), actual.getNickname());
   }

   @Test(expected=ServerFullException.class)
   public void connectToFullServerTest() throws RemoteException, ServerFullException {
      GameServer mockGameServer = new MockGameserverImpl();
      NetworkHandler[] networkHandlers = new NetworkHandlerImpl[5];
      for(int i = 0; i < 5; i++) {
        networkHandlers[i] = new NetworkHandlerImpl(new MockLobbyHandler(), mockGameServer);
      }
      for(NetworkHandler handler: networkHandlers) {
         handler.connectToServer("Player");
      }
   }
   
   @Test
   public void getSessionAddedUpdate() throws RemoteException, ServerFullException {
      GameServer mockGameServer = new MockGameserverImpl();
      
      ConcurrentLinkedQueue<NetworkMessage> updateQueue = new ConcurrentLinkedQueue<NetworkMessage>();
      MockLobbyHandler lobbyHandler = new MockLobbyHandler(updateQueue);
      NetworkHandler networkHandler = new NetworkHandlerImpl(lobbyHandler, mockGameServer);
      networkHandler.connectToServer("Player 1");
      
      lobbyHandler.receiveMessage(new SessionAddedMessage(new SessionInformation(2, "Player 2")));
      networkHandler.requestForUpdate();
      
      
   }

   @After
   public void tearDown() {
   }
   // TODO add test methods here.
   // The methods must be annotated with annotation @Test. For example:
   //
   // @Test
   // public void hello() {}

   @Override
   public void update(Observable o, Object o1) {
      updateType = (UpdateType) o1;
   }
}
