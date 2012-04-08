/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networkTest;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import network.StartGameRunnable;
import network.server.GameServerImpl;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class GameServerImplWithoutThread extends GameServerImpl {

    public GameServerImplWithoutThread(String serverName, Registry registry) throws RemoteException, MalformedURLException {
        super(serverName, registry);
    }

    @Override
    public void startGame() {
        notifyAllGameStarted();
    }
    
    
}
