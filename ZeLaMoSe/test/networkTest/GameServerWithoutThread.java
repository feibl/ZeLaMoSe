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
import network.server.GameServer;
import network.server.SessionInterface;
import org.junit.Ignore;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
@Ignore
public class GameServerWithoutThread extends GameServer {

    public GameServerWithoutThread(String serverName, Registry registry) throws RemoteException, MalformedURLException {
        super(serverName, registry);
    }

    @Override
    public void startGame() {
        notifyAllInitSignal(3);
    }

    @Override
    protected void sendInitSignal(SessionInterface s, long blockQueueSeed) {
        try {
            s.sendInitSignal(blockQueueSeed);
        } catch (RemoteException ex) {
            removeSession(s);
        }
    }

    @Override
    protected void sendStartSignal(SessionInterface s) {
        try {
            s.sendStartSignal();
        } catch (RemoteException ex) {
            removeSession(s);
        }
    }

    @Override
    protected void sendSteps(SessionInterface s, Collection<Step> removedSteps) {
        try {
            s.sendSteps(removedSteps);
        } catch (RemoteException ex) {
            removeSession(s);
        }
    }
    
    public SessionInterface getSession(int id) {
        for(SessionInterface session : sessionList) {
            if(session.getSessionInformation().getId() == id) {
                return session;
            }
        }
        return null;
    }
}
