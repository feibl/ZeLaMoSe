/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.interfaces.StepInterface;
import domain.interfaces.GameEngineInterface;
import domain.interfaces.SimulationStateInterface;
import domain.actions.Action;
import domain.actions.MoveAction;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author chrigi
 */
public class SimulationController implements StepInterface {
  private Map<Integer, Step> stepQueue = new HashMap<Integer, Step>();
  private Map<Integer, GameEngineInterface> gameEngines = new HashMap<Integer, GameEngineInterface>();
  private Map<Integer, String> sessions = new HashMap<Integer, String>();
  private final int advanceStepLimit = 20; //advance by one every 20 steps
  
  public SimulationController() {
    
  }

  @Override
  public void addStep(Step step) {
      if (stepQueue.containsKey(step.getSessionID())) {
          //throw new Exception("session");
          assert(false);
      }

      stepQueue.put(step.getSessionID(), step);
  }
  
  /*
   * Register session
   */
  public void addSession(int sessionId, String name, GameEngineInterface gameEngine) {
      assert(!gameEngines.containsKey(sessionId));
      gameEngines.put(sessionId, gameEngine);
      sessions.put(sessionId, name);
      //System.out.println("add session: "+name);
  }
  
  public void initSimulation() {
      for (GameEngineInterface e: gameEngines.values()) {
          e.startGame();
      }
  }
  
  /*
   * Execute simulation step.
   * - Look for necessary states
   * - Sort Actions
   * - Simulation ACtions
   */
  public void simulateStep(int seqNum) {
      //System.out.println("simulateStep "+seqNum);
      boolean advance = false;
      if (seqNum % advanceStepLimit == 0) {
          advance = true;
      }
      Map <Action, Integer> actionList = new TreeMap<Action, Integer>(new Comparator(){

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
          if (s.getSequenceNumber() != seqNum) {
              //throw new Exception("Invalid sequenceNumber"+s.getSequenceNumber());
              assert(false);
          }
          for (Action a: s.getActions()) {
              actionList.put(a, session);
          }
      }
      assert(stepQueue.isEmpty());
      //System.out.println("simulate actions: "+actionList.entrySet().size());
      if (advance) {
          for (GameEngineInterface g: gameEngines.values()) {
              g.handleAction(new MoveAction(0, MoveAction.Direction.DOWN, 1));
          }
      }
      for (Map.Entry<Action, Integer> e: actionList.entrySet()) {
          assert (gameEngines.containsKey(e.getValue()));
          GameEngineInterface g = gameEngines.get(e.getValue());
          g.handleAction(e.getKey());
      }
  }
  
  SimulationStateInterface getSimulation(int sessionId) {
      return gameEngines.get(sessionId);
  }
    
}
