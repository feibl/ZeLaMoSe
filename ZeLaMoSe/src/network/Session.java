/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.rmi.RemoteException;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public interface Session {
   public SessionInformation getSessionInformation();
   public ClientRemote getClientRemote();
}
