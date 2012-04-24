/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;
import network.client.Handler;
import network.client.NetworkHandler;
import network.server.GameServerRemoteInterface;
import network.server.SessionRemoteInterface;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class ConnectionRunnable implements Runnable {

   private NetworkHandler networkHandler;
   private String ip;
   private String serverName;
   private String nickname;

   public ConnectionRunnable(NetworkHandler networkHandler, String ip, String serverName, String nickname) {
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

         if (lookupObject instanceof GameServerRemoteInterface) {
            GameServerRemoteInterface server = (GameServerRemoteInterface) lookupObject;
            Handler handler = new Handler(networkHandler);
            networkHandler.setHandler(handler);
            SessionRemoteInterface ownSession = server.createSession(nickname, handler);
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
