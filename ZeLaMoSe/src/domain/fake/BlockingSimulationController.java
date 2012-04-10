/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.fake;

import domain.SimulationController;
import domain.Step;
import domain.actions.Action;
import domain.actions.MoveAction;
import domain.interfaces.GameEngineInterface;
import domain.interfaces.SimulationStateInterface;
import domain.interfaces.StepInterface;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class BlockingSimulationController extends SimulationController implements StepInterface{

    private ArrayBlockingQueue<Step> stepQueue = new ArrayBlockingQueue<Step>(4);
    private Map<Integer, GameEngineInterface> gameEngines = new HashMap<Integer, GameEngineInterface>();
    private Map<Integer, String> sessions = new HashMap<Integer, String>();
    private final int advanceStepLimit = 20; //advance by one every 20 steps

    @Override
    public void addStep(Step step) {
        stepQueue.add(step);
    }

    /*
     * Register session
     */
    @Override
    public void addSession(int sessionId, String name, GameEngineInterface gameEngine) {
        assert (!gameEngines.containsKey(sessionId));
        gameEngines.put(sessionId, gameEngine);
        sessions.put(sessionId, name);
        //System.out.println("add session: "+name);
    }

    @Override
    public void initSimulation() {
        for (GameEngineInterface e : gameEngines.values()) {
            e.startGame();
        }
    }

    /*
     * Execute simulation step. - Look for necessary states - Sort Actions - Simulation ACtions
     */
    @Override
    public void simulateStep(int seqNum) {
        //System.out.println("simulateStep "+seqNum);
        boolean advance = false;
        if (seqNum % advanceStepLimit == 0) {
            advance = true;
        }
        Map<Action, Integer> actionList = new TreeMap<Action, Integer>(new Comparator() {

            @Override
            public int compare(Object t, Object t1) {
                if (((Action) t).getTimestamp() < ((Action) t1).getTimestamp()) {
                    return -1;
                }
                return 1;
            }
        });

        for (int session : sessions.keySet()) {
            Step s = null;
            try {
                s = stepQueue.take();
            } catch (InterruptedException ex) {
                Logger.getLogger(BlockingSimulationController.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (s.getSequenceNumber() != seqNum) {
                //throw new Exception("Invalid sequenceNumber"+s.getSequenceNumber());
                assert (false);
            }
            for (Action a : s.getActions()) {
                actionList.put(a, session);
            }
        }
        if (advance) {
            for (GameEngineInterface g : gameEngines.values()) {
                g.handleAction(new MoveAction(0, MoveAction.Direction.DOWN, 1));
            }
        }
        for (Map.Entry<Action, Integer> e : actionList.entrySet()) {
            assert (gameEngines.containsKey(e.getValue()));
            GameEngineInterface g = gameEngines.get(e.getValue());
            g.handleAction(e.getKey());
        }
    }

    @Override
    public SimulationStateInterface getSimulation(int sessionId) {
        return gameEngines.get(sessionId);
    }
}
