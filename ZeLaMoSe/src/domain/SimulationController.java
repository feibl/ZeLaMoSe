package domain;

import domain.actions.Action;
import domain.actions.GameOverAction;
import domain.actions.MoveAction;
import java.util.*;
import network.server.GameServer;

/**
 *
 * @author Christian Mollekopf <cmolleko@hsr.ch>
 */
public class SimulationController implements StepInterface, Observer {
    public static final int STEPS_UNTIL_RESTART = 100;

    private SortedMap<Integer, GameEngine> rankingMap = new TreeMap<Integer, GameEngine>(new Comparator() {

        @Override
        public int compare(Object t, Object t1) {
            return ((Integer) t1).compareTo((Integer) t);
        }
    });
    private SortedMap<Integer, GameEngine> gameEngines = new TreeMap<Integer, GameEngine>();
    private SortedMap<Integer, String> sessions = new TreeMap<Integer, String>();
    private SortedMap<Integer, Step> stepQueue = new TreeMap<Integer, Step>();
    private Set<Integer> gameOverList = new HashSet<Integer>();
    private int currentHighestLevel = 1;
    private boolean autoadvance = true;
    private boolean restartNeeded;
    private int stepsUntilRestart;

    public SimulationController() {
        this(true);
    }

    public SimulationController(boolean autoadvance) {
        this.autoadvance = autoadvance;
    }

    @Override
    public void addStep(Step step) {
        if (!sessions.containsKey(step.getSessionID())) {
            throw new IllegalStateException("this session is not part of the simulation");
        }
        if (stepQueue.containsKey(step.getSessionID())) {
            throw new IllegalStateException("step queue already contains a step from this session");
        }
        stepQueue.put(step.getSessionID(), step);
    }

    public void addSession(int sessionId, String name, GameEngine gameEngine) {
        if (gameEngines.containsKey(sessionId)) {
            throw new IllegalStateException("session already added");
        }
        gameEngine.addObserver(this);
        gameEngines.put(sessionId, gameEngine);
        sessions.put(sessionId, name);
        gameEngine.setNickName(name);
    }

    public void initSimulation() {
        for (GameEngine e : gameEngines.values()) {
            e.setLevel(currentHighestLevel);
            e.startGame();
        }
    }

    /*
     * Execute simulation step. - Look for necessary states - Sort Actions - Simulation ACtions
     */
    public void simulateStep(int seqNum) {
        if (restartNeeded) {
            if (--stepsUntilRestart == 0) {
                restart();
            } else {
                for(GameEngine engine: gameEngines.values()) {
                    engine.restartCountdown(stepsUntilRestart * GameServer.STEP_DURATION);
                }
            }
        }
        SortedMap<Action, Integer> actionList = new TreeMap<Action, Integer>(new Comparator() {

            @Override
            public int compare(Object t, Object t1) {
                if (((Action) t).getTimestamp() < ((Action) t1).getTimestamp()) {
                    return -1;
                }
                return 1;
            }
        });

        fillActionList(seqNum, actionList);

        if (autoadvance && (seqNum % (Config.advanceStepLimit - currentHighestLevel) == 0)) {
            rankingMap.clear();
            for (GameEngine g : gameEngines.values()) {
                g.handleAction(new MoveAction(0, MoveAction.Direction.DOWN, 1));
                if (g.getLevel() > currentHighestLevel && currentHighestLevel <= Config.maxLevelForSpeed) {
                    currentHighestLevel = g.getLevel();
                }
                rankingMap.put(g.getScore(), g);
            }
        }

        //Distribute Ranking
        int rank = 1;
        for (Map.Entry<Integer, GameEngine> e : rankingMap.entrySet()) {
            e.getValue().setRank(rank++);
        }

        distributeActions(actionList);

        if (!stepQueue.isEmpty()) {
            throw new IllegalStateException("stepQueue not empty");
        }
    }

    private void distributeActions(SortedMap<Action, Integer> actionList) {
        for (Map.Entry<Action, Integer> e : actionList.entrySet()) {
            if (!gameEngines.containsKey(e.getValue())) {
                throw new IllegalStateException("Could not find gameEngine");
            }
            GameEngine g = gameEngines.get(e.getValue());
            if (g.getSessionID() != e.getValue()) {
                throw new IllegalStateException("wrong session id: " + e.getValue());
            }
            g.handleAction(e.getKey());
            if (g.getLevel() > currentHighestLevel && currentHighestLevel < Config.maxLevelForSpeed) {
                currentHighestLevel = g.getLevel();
            }
        }
    }

    private void fillActionList(int seqNum, Map<Action, Integer> actionList) throws IllegalStateException {
        for (int session : sessions.keySet()) {
            Step s = stepQueue.remove(session);
            if (s == null) {
                throw new IllegalStateException("tried to simulate step, but not all steps are available. Session " + session + " is missing");
            }
            if (s.getSequenceNumber() != seqNum) {
                throw new IllegalStateException("Invalid sequenceNumber" + s.getSequenceNumber());
            }
            if (s.getSessionID() != session) {
                throw new IllegalStateException("Invalid session " + s.getSessionID());
            }
            for (Action a : s.getActions()) {
                actionList.put(a, session);
            }
        }
        if (!stepQueue.isEmpty()) {
            throw new IllegalStateException("Step Queue not empty");
        }
    }

    public SimulationStateAbstract getSimulationStateInterface(int sessionId) {
        if (!gameEngines.containsKey(sessionId)) {
            throw new IllegalStateException("gameEngine not avilable " + sessionId);
        }
        return gameEngines.get(sessionId);
    }

    public void addActionForOthers(Integer sessionFrom, Action action) {
        for (int session : sessions.keySet()) {
            if (session != sessionFrom) {
                gameEngines.get(session).handleAction(action);
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if ((SimulationStateAbstract.UpdateType) arg == SimulationStateAbstract.UpdateType.ACTIONFOROTHERS) {
            addActionForOthers(((GameEngine) o).getSessionID(), ((GameEngine) o).getlastActionForOthers());
        } else if ((SimulationStateAbstract.UpdateType) arg == SimulationStateAbstract.UpdateType.GAME_OVER) {
            gameOverList.add(((GameEngine) o).getSessionID());
            checkForRestart();
        }
    }

    public void removeSession(int sessionId) {
        if (gameEngines.get(sessionId) == null) { //The session has already been removed
            return;
        }
        GameEngine removedGameEngine = gameEngines.remove(sessionId);
        removedGameEngine.deleteObserver(this);
        stepQueue.remove(sessionId);
        sessions.remove(sessionId);
        removedGameEngine.setGameOver();
        checkForRestart();
    }

    public void setLevel(int level) {
        this.currentHighestLevel = level;
    }

    private void restart() {
        restartNeeded = false;
        currentHighestLevel = 1;
        gameOverList.clear();
        for (GameEngine gameEngine : gameEngines.values()) {
            gameEngine.restart(1, 1, true, 1);
        }
    }

    private void checkForRestart() {
        if (gameOverList.containsAll(sessions.keySet())) {
            restartNeeded = true;
            //5 Seconds
            stepsUntilRestart = STEPS_UNTIL_RESTART;
        }
    }
}
