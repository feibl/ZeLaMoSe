package domain;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;
import network.ChatMessage;
import network.SessionInformation;
import network.client.NetworkHandlerAbstract;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class ChatController extends Observable implements Observer {

    private NetworkHandlerAbstract nH;

    public ChatController(NetworkHandlerAbstract nH) {
        this.nH = nH;
        nH.addObserver(this);
    }

    public ConcurrentHashMap<Integer, String> getSessionList() {
        return nH.getSessionList();
    }

    public SessionInformation getOwnSession() {
        return nH.getLocalSession();
    }

    public void tearDown() {
        nH.deleteObserver(this);
    }

    public String getOwnIpAddress() throws UnknownHostException {
        InetAddress in = InetAddress.getLocalHost();
        InetAddress ip = InetAddress.getByName(in.getHostName());
        return ip.getHostAddress();
    }

    public ChatMessage getChatMessage() {
        return nH.getChatMessage();
    }

    public SessionInformation getAddedSession() {
        return nH.getAddedSession();
    }

    public SessionInformation getRemovedSession() {
        return nH.getRemovedSession();
    }

    public void sendChatMessage(String message) {
        nH.sendChatMessage(message);
    }

    @Override
    public void update(Observable o, Object o1) {
        switch ((TetrisController.UpdateType) o1) {
            case CHAT_MESSAGE_RECEIVED:
                setChanged();
                notifyObservers(o1);
                break;
            case SESSION_ADDED:
                setChanged();
                notifyObservers(o1);
                break;
            case SESSION_REMOVED:
                setChanged();
                notifyObservers(o1);
                break;
        }
    }
}
