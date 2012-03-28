/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import network.NetworkHandler;
import domain.Step;
import domain.StepInterface;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentLinkedQueue;
import network.Session;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class NetworkHandlerImpl extends NetworkHandler {

   private Handler handler;
   private Session session;
   private ConcurrentLinkedQueue updateQueue;

   public NetworkHandlerImpl(Handler handler) {
      this.handler = handler;
   }

   public NetworkHandlerImpl() {
   }

   @Override
   public SessionInformation getAddedSession() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public int getRandomGeneratorSeed() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public SessionInformation getRemovedSession() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public SessionInformation connectToServer(String nickname, String ip, String servername) throws MalformedURLException, NotBoundException, RemoteException {
      Object lookupObject = Naming.lookup("rmi://" + ip + "/" + servername);
      System.out.println(lookupObject.toString());
      
      if (lookupObject instanceof GameServer) {
         GameServer server = (GameServer) lookupObject;
         session = server.createSession(nickname, handler);
         
         return session.getSessionInformation();
      } else {
         throw new NotBoundException();
      }
   }

   @Override
   public void addStep(Step step) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public Step getStep() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void disconnectFromServer() {
      throw new UnsupportedOperationException("Not supported yet.");
   }
}
