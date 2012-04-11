/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.fake;

import domain.Config;
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
public class BlockingSimulationController extends SimulationController implements StepInterface {

    private ArrayBlockingQueue<Step> stepQueue = new ArrayBlockingQueue<Step>(4);

    @Override
    public void addStep(Step step) {
        stepQueue.add(step);
    }

    /*
     * Execute simulation step. - Look for necessary states - Sort Actions - Simulation ACtions
     */
    @Override
    public void simulateStep(int seqNum) {
        //System.out.println("simulateStep "+seqNum);
        boolean advance = false;
        if (autoadvance && (seqNum % (Config.advanceStepLimit - maxLevel) == 0)) {
            advance = true;
        }
        Map<Action, Integer> actionList = new TreeMap<Action, Integer>(new Comparator() {

            @Override
            public int compare(Object t, Object t1) {
                if (((Action) t).getTimestamp() < ((Action) t1).getTimestamp()) {
                    return -1;
                }
                return -1;
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
        for (Map.Entry<Action, Integer> e : sortEntrySet(actionList.entrySet())) {
            System.out.println("--Simulating action with timestamp: " + e.getKey().getTimestamp() + " sessionid " + e.getValue());
            assert (gameEngines.containsKey(e.getValue()));
            GameEngineInterface g = gameEngines.get(e.getValue());
            assert (g.getSessionID() == e.getValue());
            g.handleAction(e.getKey());
            if (g.getLevel() > maxLevel && maxLevel < Config.maxLevelForSpeed) {
                maxLevel = g.getLevel();
            }
        }
    }
}
