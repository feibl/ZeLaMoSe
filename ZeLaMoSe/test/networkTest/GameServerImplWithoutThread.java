/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networkTest;

import domain.Step;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
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
    protected void distributeStepToOthers(Session sender, Step step) {
        final Step stepFinal = step;
        for (final Session s : sessionList) {
            if (s != null && s != sender) {
                try {
                    s.sendStep(stepFinal);
                } catch (RemoteException ex) {
                    removeSession(s);
                }
            }
        }
    }

    @Override
    protected void notifyAllGameStarted() {
        for (int i = 0; i < sessionList.length; i++) {
            final Session s = sessionList[i];
            if (s != null) {
                try {
                    s.sendStartSignal();
                } catch (RemoteException ex) {
                    removeSession(s);
                }
            }
        }
    }

    @Override
    protected void notifyAllInitSignal(long blockQueueSeed) {
        for (int i = 0; i < sessionList.length; i++) {
            final Session s = sessionList[i];
            if (s != null) {
                try {
                    s.sendInitSignal(blockQueueSeed);
                } catch (RemoteException ex) {
                    removeSession(s);
                }
            }
        }
    }
}
