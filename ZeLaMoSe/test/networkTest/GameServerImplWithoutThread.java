/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networkTest;

import domain.Step;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.server.GameServerImpl;
import network.server.Session;
import org.junit.Ignore;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
@Ignore
public class GameServerImplWithoutThread extends GameServerImpl {

    public GameServerImplWithoutThread(String serverName, Registry registry) throws RemoteException, MalformedURLException {
        super(serverName, registry);
    }

    @Override
    public void startGame() {
        notifyAllInitSignal(3);
    }

    @Override
    protected void sendInitSignal(Session s, long blockQueueSeed) {
        try {
            s.sendInitSignal(blockQueueSeed);
        } catch (RemoteException ex) {
            Logger.getLogger(GameServerImplWithoutThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void sendStartSignal(Session s) {
        try {
            s.sendStartSignal();
        } catch (RemoteException ex) {
            Logger.getLogger(GameServerImplWithoutThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void sendSteps(Session s, Collection<Step> removedSteps) {
        try {
            s.sendSteps(removedSteps);
        } catch (RemoteException ex) {
            Logger.getLogger(GameServerImplWithoutThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
