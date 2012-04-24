/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.actions.Action;
import domain.actions.GarbageLineAction;
import domain.actions.MoveAction;
import java.util.*;

/**
 *
 * @author Christian Mollekopf <cmolleko@hsr.ch>
 */
public class SimulationController implements StepInterface {

    private Map<Integer, Step> stepQueue = new HashMap<Integer, Step>();
    private Map<Integer, GameEngineAbstract> gameEngines = new HashMap<Integer, GameEngineAbstract>();
    private Map<Integer, String> sessions = new HashMap<Integer, String>();
    private int currentHighestLevel = 1;
    public boolean autoadvance = true;

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
        gameEngines.put(sessionId, gameEngine);
        sessions.put(sessionId, name);
        gameEngine.setSimulationController(this);
        gameEngine.setNickName(name);
    }

    public void initSimulation() {
        for (GameEngineAbstract e : gameEngines.values()) {
            e.startGame();
        }
    }

    /*
     * This is just a workaround because the map doesn't seem to sort as expected, at least not with the entry set.
     */
    protected ArrayList< Map.Entry<Action, Integer>> sortEntrySet(Set< Map.Entry<Action, Integer>> set) {
        ArrayList< Map.Entry<Action, Integer>> sortedList = new ArrayList< Map.Entry<Action, Integer>>();
        for (Map.Entry<Action, Integer> e : set) {
            int i = 0;
            for (Map.Entry<Action, Integer> e1 : sortedList) {
                if (e.getKey().getTimestamp() < e1.getKey().getTimestamp()) {
                    break;
                }
                i++;
            }
            sortedList.add(i, e);
        }
        return sortedList;
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
            for (GameEngineAbstract g : gameEngines.values()) {
                g.handleAction(new MoveAction(0, MoveAction.Direction.DOWN, 1));
            }
        }
        distributeActions(actionList);

        for (GameEngineAbstract g : gameEngines.values()) {
            if (g.getLevel() > currentHighestLevel && currentHighestLevel < Config.maxLevelForSpeed) {
                currentHighestLevel = g.getLevel();
            }
        }

        if (!stepQueue.isEmpty()) {
            throw new IllegalStateException("stepQueue not empty");
        }
    }

    private void distributeActions(Map<Action, Integer> actionList) {
        for (Map.Entry<Action, Integer> e : sortEntrySet(actionList.entrySet())) {
            //System.out.println("--Simulating action with timestamp: " + e.getKey().getTimestamp() + " sessionid " + e.getValue());
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

    public void addGarbageLineAction(Integer sessionFrom, GarbageLineAction action) {
        for (int session : sessions.keySet()) {
            if (session != sessionFrom) {
                gameEngines.get(session).handleAction(action);
            }
        }
    }
}
