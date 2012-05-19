/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.actions.Action;
import domain.actions.GameOverAction;
import domain.actions.MoveAction;
import java.util.*;

/**
 *
 * @author Christian Mollekopf <cmolleko@hsr.ch>
 */
public class SimulationController implements StepInterface, Observer {

    private SortedMap<Integer, GameEngineAbstract> rankingMap = new TreeMap<Integer, GameEngineAbstract>(new Comparator() {

        @Override
        public int compare(Object t, Object t1) {
            return ((Integer) t1).compareTo((Integer) t);
        }
    });
    private Map<Integer, GameEngineAbstract> gameEngines = new HashMap<Integer, GameEngineAbstract>();
    private SortedMap<Integer, String> sessions = new TreeMap<Integer, String>();
    private Map<Integer, Step> stepQueue = new HashMap<Integer, Step>();
    private int currentHighestLevel = 1;
    private boolean autoadvance = true;

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

    /*
     * Register session
     */
    public void addSession(int sessionId, String name, GameEngineAbstract gameEngine) {
        if (gameEngines.containsKey(sessionId)) {
            throw new IllegalStateException("session already added");
        }
        gameEngine.addObserver(this);
        gameEngines.put(sessionId, gameEngine);
        sessions.put(sessionId, name);
        gameEngine.setNickName(name);
    }

    public void initSimulation() {
        for (GameEngineAbstract e : gameEngines.values()) {
            e.setLevel(currentHighestLevel);
            e.startGame();
        }
    }

    /*
     * Execute simulation step. - Look for necessary states - Sort Actions - Simulation ACtions
     */
    public void simulateStep(int seqNum) {
        Map<Action, Integer> actionList = new TreeMap<Action, Integer>(new Comparator() {

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
            for (GameEngineAbstract g : gameEngines.values()) {
                g.handleAction(new MoveAction(0, MoveAction.Direction.DOWN, 1));
                if (g.getLevel() > currentHighestLevel && currentHighestLevel < Config.maxLevelForSpeed) {
                    currentHighestLevel = g.getLevel();
                }
                rankingMap.put(g.getScore(), g);
            }
        }

        //Distribute Ranking
        int rank = sessions.size();
        for (Map.Entry<Integer, GameEngineAbstract> e : rankingMap.entrySet()) {
            e.getValue().setRank(rank--);
        }

        distributeActions(actionList);

        if (!stepQueue.isEmpty()) {
            throw new IllegalStateException("stepQueue not empty");
        }
    }

    private void distributeActions(Map<Action, Integer> actionList) {
        for (Map.Entry<Action, Integer> e : actionList.entrySet()) {
            System.out.println("handleAction: " + e.getValue().toString() + " " + e.getKey().getType() + " " + e.getKey().getTimestamp());
            if (!gameEngines.containsKey(e.getValue())) {
                throw new IllegalStateException("Could not find gameEngine");
            }
            GameEngineAbstract g = gameEngines.get(e.getValue());
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
            //System.out.println("step: "+s+" session: "+session);
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
            addActionForOthers(((GameEngineAbstract) o).getSessionID(), ((GameEngineAbstract) o).getlastActionForOthers());
        }
    }

    public void removeSession(int sessionId) {
        if (gameEngines.get(sessionId) == null) { //The session has already been removed
            return;
        }
        gameEngines.get(sessionId).handleAction(new GameOverAction(0));
        gameEngines.remove(sessionId).deleteObserver(this);
        stepQueue.remove(sessionId);
        sessions.remove(sessionId);
    }

    void setLevel(int level) {
        this.currentHighestLevel = level;
    }
}
