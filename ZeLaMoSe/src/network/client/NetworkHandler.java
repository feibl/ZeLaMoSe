package network.client;

import domain.Step;
import domain.TetrisController.UpdateType;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import network.ChatMessage;
import network.GameParams;
import network.SessionInformation;
import network.server.GameServerRemoteInterface;
import network.server.SessionRemoteInterface;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class NetworkHandler extends NetworkHandlerAbstract {

    private ConcurrentHashMap<Integer, String> sessionList = new ConcurrentHashMap<Integer, String>();
    private BlockingQueue<Collection<Step>> stepQueue = new LinkedBlockingQueue<Collection<Step>>();
    private SessionInformation localSessionInformation;
    private SessionInformation lastRemovedSession;
    private SessionInformation lastAddedSession;
    private ExecutorService threadPool;
    private Exception thrownException;
    private Handler handler;
    private ChatMessage chatMessage;
    private GameParams gameParams;
    private Step lastStep;

    @Override
    public void processStep() {
        try {
            Collection<Step> steps = stepQueue.poll(10000, TimeUnit.MILLISECONDS);
            if (steps != null) {
                for (Step step : steps) {
                    lastStep = step;
                    setChanged();
                    notifyObservers(UpdateType.STEP);
                }
            } else {
                setChanged();
                notifyObservers(UpdateType.TIMED_OUT);
            }
        } catch (InterruptedException ex) {
            throw new IllegalStateException("take from step queue was interrupted");
        }
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public Handler getHandler() {
        return handler;
    }

    public NetworkHandler() {
        threadPool = Executors.newFixedThreadPool(1);
    }

    @Override
    public SessionInformation getAddedSession() {
        return lastAddedSession;
    }

    @Override
    public SessionInformation getRemovedSession() {
        return lastRemovedSession;
    }

    @Override
    public void connectToServer(final String ip, final String serverName, final String nickname) {
        threadPool.submit(new Runnable() {

            @Override
            public void run() {
                runConnectToServer(ip, serverName, nickname);
            }
        });
    }

    protected void runConnectToServer(final String ip, final String serverName, final String nickname) {
        try {
            Object lookupObject = Naming.lookup("rmi://" + ip + '/' + serverName);

            if (lookupObject instanceof GameServerRemoteInterface) {
                GameServerRemoteInterface server = (GameServerRemoteInterface) lookupObject;
                Handler tempHandler = new Handler(this);
                setHandler(tempHandler);
                SessionRemoteInterface sessionRemote = server.createSession(nickname, tempHandler);
                handler.setSessionRemote(sessionRemote);
                notifyConnectionEstablished(sessionRemote.getRemoteSessionInformation(), server.getSessionList());
            } else {
                throw new RemoteException();
            }
        } catch (Exception ex) {
            notifyExceptionThrown(ex);
        }
    }

    @Override
    public void addStep(final Step step) {
        threadPool.execute(new Runnable() {

            @Override
            public void run() {
                runAddStep(step);
            }
        });
    }

    protected void runAddStep(Step step) {
        handler.sendStep(step);
    }

    @Override
    public Step getStep() {
        return lastStep;
    }

    @Override
    public void disconnectFromServer() {
        threadPool.execute(new Runnable() {

            @Override
            public void run() {
                runDisconnectFromServer();
            }
        });
    }

    protected void runDisconnectFromServer() {
        handler.disconnect();
    }

    public void notifyStepsReceived(Collection<Step> steps) {
        stepQueue.add(steps);
    }

    public void notifySessionAdded(SessionInformation addedSession) {
        lastAddedSession = addedSession;
        sessionList.put(addedSession.getId(), addedSession.getNickname());
        setChanged();
        notifyObservers(UpdateType.SESSION_ADDED);
    }

    public void notifySessionRemoved(SessionInformation removedSession) {
        lastRemovedSession = removedSession;
        sessionList.remove(new Integer(removedSession.getId()));
        setChanged();
        notifyObservers(UpdateType.SESSION_REMOVED);
    }

    public void notifyExceptionThrown(Exception ex) {
        thrownException = ex;
        setChanged();
        notifyObservers(UpdateType.EXCEPTION_THROWN);
    }

    @Override
    public SessionInformation getLocalSessionInformation() {
        return localSessionInformation;
    }

    public void notifyConnectionEstablished(SessionInformation ownSession, List<SessionInformation> sessionList) {
        this.localSessionInformation = ownSession;
        for (SessionInformation session : sessionList) {
            this.sessionList.put(session.getId(), session.getNickname());
        }
        setChanged();
        notifyObservers(UpdateType.CONNECTION_ESTABLISHED);
    }

    @Override
    public ConcurrentHashMap<Integer, String> getSessionMap() {
        return sessionList;
    }

    @Override
    public void sendChatMessage(final String message) {
        threadPool.execute(new Runnable() {

            @Override
            public void run() {
                runSendChatMessage(message);
            }
        });
    }

    protected void runSendChatMessage(final String message) {
        handler.sendChatMessage(message);
    }

    void notifyChatMessageReceived(ChatMessage message) {
        this.chatMessage = message;
        setChanged();
        notifyObservers(UpdateType.CHAT_MESSAGE_RECEIVED);
    }

    @Override
    public ChatMessage getChatMessage() {
        return chatMessage;
    }

    @Override
    public Exception getThrownException() {
        return thrownException;
    }

    public void notifyInit(GameParams gameParams) {
        this.gameParams = gameParams;
        setChanged();
        notifyObservers(UpdateType.INIT_SIGNAL);
    }

    public void notifyGameStarted() {
        setChanged();
        notifyObservers(UpdateType.GAME_STARTED);
    }

    @Override
    public ExecutorService getThreadPool() {
        return threadPool;
    }

    @Override
    public void sendReadySignal() {
        handler.sendReadySignal();
    }

    @Override
    public GameParams getGameParams() {
        return gameParams;
    }
}
