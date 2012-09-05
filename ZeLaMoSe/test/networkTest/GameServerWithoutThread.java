package networkTest;

import domain.Step;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.Collection;
import network.GameParams;
import network.server.GameServer;
import network.server.Session;
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
    protected void sendInitSignal(Session s, GameParams initParameter) {
        try {
            s.sendInitSignal(initParameter);
        } catch (RemoteException ex) {
            removeSession(s);
        }
    }

    @Override
    protected void sendStartSignal(Session s) {
        try {
            s.sendStartSignal();
        } catch (RemoteException ex) {
            removeSession(s);
        }
    }

    @Override
    protected void sendSteps(Session s, Collection<Step> removedSteps) {
        try {
            s.sendSteps(removedSteps);
        } catch (RemoteException ex) {
            removeSession(s);
        }
    }

    public Session getSession(int id) {
        for (Session session : sessionList) {
            if (session.getSessionInformation().getId() == id) {
                return session;
            }
        }
        return null;
    }
}
