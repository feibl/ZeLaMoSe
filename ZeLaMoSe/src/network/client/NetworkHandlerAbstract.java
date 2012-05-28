package network.client;

import domain.StepInterface;
import domain.StepProducerInterface;
import java.util.Observable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import network.ChatMessage;
import network.GameParams;
import network.SessionInformation;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public abstract class NetworkHandlerAbstract extends Observable implements StepInterface, StepProducerInterface {

    public abstract SessionInformation getAddedSession();

    public abstract SessionInformation getRemovedSession();

    public abstract void connectToServer(String ip, String serverName, String nickname);

    public abstract void disconnectFromServer();

    public abstract SessionInformation getLocalSession();

    public abstract ConcurrentHashMap<Integer, String> getSessionMap();

    public abstract void sendChatMessage(String message);

    public abstract ChatMessage getChatMessage();

    public abstract Exception getThrownException();

    public abstract ExecutorService getThreadPool();

    public abstract GameParams getGameParams();

    public abstract void sendReadySignal();
}
