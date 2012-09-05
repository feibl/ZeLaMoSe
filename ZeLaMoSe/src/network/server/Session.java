package network.server;

import domain.Step;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import network.ChatMessage;
import network.GameParams;
import network.SessionInformation;
import network.client.ClientRemoteInterface;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class Session extends UnicastRemoteObject implements SessionRemoteInterface, SessionInterface {

    private ClientRemoteInterface client;
    private GameServer gameServer;
    private SessionInformation sessionInformation;

    public Session(SessionInformation sessionInformation, ClientRemoteInterface client, GameServer gameServer) throws RemoteException {
        this.sessionInformation = sessionInformation;
        this.gameServer = gameServer;
        this.client = client;
    }

    @Override
    public void disconnect() throws RemoteException {
        gameServer.removeSession(this);
    }

    @Override
    public void receiveChatMessage(String message) throws RemoteException {
        gameServer.postChatMessage(this, message);
    }

    @Override
    public void receiveStep(Step step) throws RemoteException {
        gameServer.addStep(this, step);
    }

    @Override
    public SessionInformation getRemoteSessionInformation() throws RemoteException {
        return getSessionInformation();
    }

    @Override
    public SessionInformation getSessionInformation() {
        return sessionInformation;
    }

    @Override
    public void sendChatMessage(SessionInformation sender, String message) throws RemoteException {
        client.receiveChatMessage(new ChatMessage(sender, message));
    }

    @Override
    public void sendSteps(Collection<Step> steps) throws RemoteException {
        client.receiveSteps(steps);
    }

    @Override
    public void sendSessionAddedMessage(SessionInformation sessionInfo) throws RemoteException {
        client.receiveSessionAddedMessage(sessionInfo);
    }

    @Override
    public void sendSessionRemovedMessage(SessionInformation sessionInfo) throws RemoteException {
        client.receiveSessionRemovedMessage(sessionInfo);
    }

    @Override
    public void sendStartSignal() throws RemoteException {
        client.receiveStartSignal();
    }

    @Override
    public void receiveReadySignal() throws RemoteException {
        gameServer.notifyReadySignalReceived(this);
    }

    @Override
    public void sendInitSignal(GameParams gameParams) throws RemoteException {
        client.receiveInitSignal(gameParams);
    }
}
