/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network.client;

import domain.Step;
import domain.TetrisController.UpdateType;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import network.*;
import network.server.GameServerRemoteInterface;
import network.server.SessionRemoteInterface;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class NetworkHandler extends NetworkHandlerAbstract {

    private HandlerInterface handler;
    private BlockingQueue<Collection<Step>> stepQueue = new LinkedBlockingQueue<Collection<Step>>();
    private Step lastStep;
    private SessionInformation lastAddedSession;
    private SessionInformation lastRemovedSession;
    private SessionInformation ownSession;
    private ChatMessage chatMessage;
    private ExecutorService threadPool;
    private ConcurrentHashMap<Integer, String> sessionList = new ConcurrentHashMap<Integer, String>();
    private Exception thrownException;
    private long blockQueueSeed;
    private int numberOfJokers;

    @Override
    public void processStep() {
        try {
            Collection<Step> steps = stepQueue.poll(5000, TimeUnit.MILLISECONDS);
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

    public void setHandler(HandlerInterface handler) {
        this.handler = handler;
    }

    public HandlerInterface getHandler() {
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
    public int getRandomGeneratorSeed() {
        throw new UnsupportedOperationException("Not supported yet.");
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
    public SessionInformation getOwnSession() {
        return ownSession;
    }

    public void notifyConnectionEstablished(SessionInformation ownSession, List<SessionInformation> sessionList) {
        this.ownSession = ownSession;
        for (SessionInformation session : sessionList) {
            this.sessionList.put(session.getId(), session.getNickname());
        }
        setChanged();
        notifyObservers(UpdateType.CONNECTION_ESTABLISHED);
    }

    @Override
    public ConcurrentHashMap<Integer, String> getSessionList() {
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

    public void notifyInit(long blockQueueSeed, int numberOfJokers) {
        this.blockQueueSeed = blockQueueSeed;
        this.numberOfJokers = numberOfJokers;
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
    public long getBlockQueueSeed() {
        return blockQueueSeed;
    }

    @Override
    public int getNumberOfJokers() {
        return numberOfJokers;
    }
}
