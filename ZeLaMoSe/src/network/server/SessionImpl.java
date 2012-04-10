/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network.server;

import domain.Step;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import network.ChatMessage;
import network.SessionInformation;
import network.client.ClientRemote;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class SessionImpl extends UnicastRemoteObject implements SessionRemote, Session  {
    private ClientRemote client;
    private GameServerImpl gameServer;
    private SessionInformation sessionInformation;
    
    public SessionImpl(SessionInformation sessionInformation, ClientRemote client, GameServerImpl gameServer) throws RemoteException {
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
        gameServer.distributeStepToOthers(this, step);
    }

    @Override
    public SessionInformation getSessionInformation() throws RemoteException {
        return sessionInformation;
    }

    @Override
    public void sendChatMessage(SessionInformation sender, String message) throws RemoteException {
        client.receiveChatMessage(new ChatMessage(sender, message));
    }

    @Override
    public void sendStep(Step step) throws RemoteException {
        client.receiveStep(step);
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
    public void sendInitSignal() throws RemoteException {
        client.receiveInitSignal();
    }
    
}
