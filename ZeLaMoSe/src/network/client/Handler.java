/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network.client;

import domain.Config;
import domain.Step;
import java.io.File;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;
import network.ChatMessage;
import network.SessionInformation;
import network.server.GameServer;
import network.server.SessionRemoteInterface;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class Handler extends UnicastRemoteObject implements HandlerInterface, ClientRemoteInterface {

    private NetworkHandler networkHandler;
    private SessionRemoteInterface sessionRemote;

    public Handler(NetworkHandler networkHandler) throws RemoteException {
        this.networkHandler = networkHandler;
        File policy= Config.convertRMI(GameServer.class);
        System.setProperty("java.security.policy", policy.getAbsolutePath() );
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }
    }

    @Override
    public void disconnect() {
        try {
            sessionRemote.disconnect();
        } catch (RemoteException ex) {
            System.out.println("Couldn't reach server while disconnected");
        }
    }

    @Override
    public void sendChatMessage(String message) {
        try {
            sessionRemote.receiveChatMessage(message);
        } catch (RemoteException ex) {
            networkHandler.notifyExceptionThrown(ex);
        }
    }

    @Override
    public void sendStep(Step step) {
        try {
            sessionRemote.receiveStep(step);
        } catch (RemoteException ex) {
            networkHandler.notifyExceptionThrown(ex);
        }
    }

    @Override
    public void receiveSteps(Collection<Step> steps) throws RemoteException {
        networkHandler.notifyStepsReceived(steps);
    }

    @Override
    public void receiveChatMessage(ChatMessage message) throws RemoteException {
        networkHandler.notifyChatMessageReceived(message);
    }

    @Override
    public void receiveSessionAddedMessage(SessionInformation session) throws RemoteException {
        networkHandler.notifySessionAdded(session);
    }

    @Override
    public void receiveSessionRemovedMessage(SessionInformation session) throws RemoteException {
        networkHandler.notifySessionRemoved(session);
    }

    @Override
    public void receiveStartSignal() throws RemoteException {
        networkHandler.notifyGameStarted();
    }

    @Override
    public void setSessionRemote(SessionRemoteInterface sessionRemote) {
        this.sessionRemote = sessionRemote;
    }

    @Override
    public void sendReadySignal() {
        try {
            sessionRemote.receiveReadySignal();
        } catch (RemoteException ex) {
            networkHandler.notifyExceptionThrown(ex);
        }
    }

    @Override
    public void receiveInitSignal(long blockQueueSeed, int numberOfJokers) throws RemoteException {
        networkHandler.notifyInit(blockQueueSeed, numberOfJokers);
    }
}
