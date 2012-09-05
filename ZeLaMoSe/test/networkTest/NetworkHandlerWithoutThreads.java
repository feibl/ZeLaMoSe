package networkTest;

import domain.Step;
import network.client.NetworkHandler;
import org.junit.Ignore;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
@Ignore
public class NetworkHandlerWithoutThreads extends NetworkHandler {

    @Override
    public void connectToServer(String ip, String serverName, String nickname) {
        runConnectToServer(ip, serverName, nickname);
    }

    @Override
    public void disconnectFromServer() {
        runDisconnectFromServer();
    }

    @Override
    public void sendChatMessage(String message) {
        runSendChatMessage(message);
    }

    @Override
    public void addStep(Step step) {
        runAddStep(step);
    }
}
