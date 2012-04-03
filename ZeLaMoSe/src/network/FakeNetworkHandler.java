/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import domain.Step;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 *
 * @author chrigi
 */
public class FakeNetworkHandler extends NetworkHandler {

    @Override
    public SessionInformation getAddedSession() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getRandomGeneratorSeed() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public SessionInformation getRemovedSession() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void connectToServer(String ip, String serverName, String nickname) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void disconnectFromServer() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public SessionInformation getOwnSession() {
        return new SessionInformation(3, "test");
    }
    
    public void setConnected() {
        setChanged();
        notifyObservers(UpdateType.CONNECTION_ESTABLISHED);
    }
    
//    public void startGame() {
//        setChanged();
//        notifyObservers(UpdateType.GAME_STARTED);
//    }

    @Override
    public List<SessionInformation> getSessionList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void sendChatMessage(String message) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ChatMessage getChatMessage() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Exception getThrownException() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ExecutorService getThreadPool() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addStep(Step step) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Step getStep() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void niggasInParis() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
