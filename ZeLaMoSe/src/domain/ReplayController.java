/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author feibl
 */
public class ReplayController {

    ReplayData replayData;
    SimulationController simulationController;
    static final int stepDuration = 50;

    public ReplayController(ReplayData replayData, SimulationController simulationController) {
        this.replayData = replayData;
        this.simulationController = simulationController;
    }

    public void run() {
        simulationController.initSimulation();
        new Thread(new Runnable() {

            int i = 0;

            @Override
            public void run() {
                while (!replayData.getSteps().isEmpty() && replayData.getSteps().containsKey(i)) {
                    List<Step> stepsequence = replayData.getSteps().get(i);
                    if (stepsequence.size() < replayData.getSessionList().size()) {
                        removeSessionWithNoStep(stepsequence);
                    }
                    for (Step step : stepsequence) {
                        simulationController.addStep(step);
                    }
                    simulationController.simulateStep(i++);

                    try {
                        Thread.sleep(stepDuration);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ReplayController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }


            }

            public void removeSessionWithNoStep(List<Step> stepsequence) {
                List<Integer> sessionsToRemove = new ArrayList<Integer>();

                for (Map.Entry<Integer, String> session : replayData.getSessionList().entrySet()) {
                    boolean found = false;
                    for (Step step : stepsequence) {
                        if (step.getSessionID() == session.getKey()) {
                            found = true;
                        }
                    }
                    if (!found) {
                        sessionsToRemove.add(session.getKey());
                        simulationController.removeSession(session.getKey());
                    }
                }
                replayData.getSessionList().keySet().removeAll(sessionsToRemove);
            }
        }).start();
    }
}
