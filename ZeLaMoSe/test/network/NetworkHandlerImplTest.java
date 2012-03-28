/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import org.junit.*;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class NetworkHandlerImplTest {

   NetworkHandlerImpl classUnderTest;

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
      classUnderTest = new NetworkHandlerImpl();
   }

   @Test
   public void connectToServerTest() throws MalformedURLException, RemoteException, NotBoundException {
      SessionInformation player1Session = new SessionInformation(1, "Player 1");
      GameServerImpl server = new GameServerImpl();
      LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
      Naming.rebind("Tetris-Server", server);
      SessionInformation actual = classUnderTest.connectToServer("Player 1", "localhost", "Tetris-Server");
      assertEquals(player1Session.getId(), actual.getId());
      assertEquals(player1Session.getNickname(), actual.getNickname());
   }

   @Test(expected=NotBoundException.class)
   public void connectToNotPresentServerTest() throws MalformedURLException, RemoteException, NotBoundException {
      classUnderTest.connectToServer("Player 1", "localhost", "Tetris-Server");
   }

   @After
   public void tearDown() {
   }
   // TODO add test methods here.
   // The methods must be annotated with annotation @Test. For example:
   //
   // @Test
   // public void hello() {}
}
