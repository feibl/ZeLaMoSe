/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import com.jogamp.common.util.RunnableTask;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.client.HandlerImpl;
import network.client.NetworkHandlerImpl;
import network.server.GameServerRemote;
import network.server.SessionRemote;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class ConnectionRunnable implements Runnable {

   private NetworkHandlerImpl networkHandler;
   private String ip;
   private String serverName;
   private String nickname;

   public ConnectionRunnable(NetworkHandlerImpl networkHandler, String ip, String serverName, String nickname) {
      this.networkHandler = networkHandler;
      this.ip = ip;
      this.serverName = serverName;
      this.nickname = nickname;
   }

   @Override
   public void run() {
      try {
         //Invoke this in a Thread with Callback Function
         Object lookupObject = Naming.lookup("rmi://" + ip + '/' + serverName);

         if (lookupObject instanceof GameServerRemote) {
            GameServerRemote server = (GameServerRemote) lookupObject;
            HandlerImpl handler = new HandlerImpl(networkHandler);
            networkHandler.setHandler(handler);
            SessionRemote ownSession = server.createSession(nickname, handler);
            List<SessionInformation> sessionList = server.getSessionList();
            handler.setSessionRemote(ownSession);
            networkHandler.notifyConnectionEstablished(ownSession.getRemoteSessionInformation(), sessionList);
         } else {
            throw new RemoteException();
         }
      } catch (NotBoundException ex) {
         networkHandler.notifyExceptionThrown(ex);
      } catch (MalformedURLException ex) {
         networkHandler.notifyExceptionThrown(ex);
      } catch (RemoteException ex) {
         networkHandler.notifyExceptionThrown(ex);
      }
   }
}
