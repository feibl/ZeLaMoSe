/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author chrigi
 */
public class SimulationController implements StepInterface {
  private Map<Integer, Step> stepQueue = new HashMap<>();
  private Map<Integer, GameEngineInterface> engines = new HashMap<>();
  private Map<Integer, String> sessions = new HashMap<>();
  
  public SimulationController() {
    
  }

  @Override
  public void addStep(Step step) {
      if (stepQueue.containsKey(step.sessionId())) {
          //throw new Exception("session");
          assert(false);
      }

      stepQueue.put(step.sessionId(), step);
  }
  
  /*
   * Register session
   */
  public void addSession(int sessionId, String name, GameEngineInterface gameEngine) {
      engines.put(sessionId, gameEngine);
      sessions.put(sessionId, name);
  }
  
  /*
   * Execute simulation step.
   * - Look for necessary states
   * - Sort Actions
   * - Simulation ACtions
   */
  public void simulateStep(int seqNum) {
      Map <Action, Integer> actionList = new TreeMap<>(new Comparator(){

          @Override
          public int compare(Object t, Object t1) {
              if (((Action)t).getTimestamp() < ((Action)t1).getTimestamp()) {
                  return -1;
              }
              return 1;
          }

      });

      for (int session: sessions.keySet()) {
          assert(stepQueue.containsKey(session));
          Step s = stepQueue.remove(session);      
          if (s.seqNum() != seqNum) {
              //throw new Exception("Invalid sequenceNumber"+s.seqNum());
              assert(false);
          }
          for (Action a: s.actions()) {
              actionList.put(a, session);
          }
      }
      assert(stepQueue.isEmpty());
      for (Map.Entry<Action, Integer> e: actionList.entrySet()) {
          assert (engines.containsKey(e.getValue()));
          GameEngineInterface g = engines.get(e.getValue());
          g.simulateAction(e.getKey());
          //engines.put(g.sessionId(), g);
      }
  }
  
  SimulationStateInterface getSimulation(int sessionId) {
      return engines.get(sessionId);
  }
    
}
