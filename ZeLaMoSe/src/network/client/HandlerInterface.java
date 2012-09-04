package network.client;

import domain.Step;
import network.server.SessionRemoteInterface;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public interface HandlerInterface {

    public void disconnect();

    public void sendChatMessage(String message);

    public void sendStep(Step step);

    public void setSessionRemote(SessionRemoteInterface sessionRemote);

    public void sendReadySignal();

    public void sendRestartRequest();
}
