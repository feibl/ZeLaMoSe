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
  private int maxLevel = 1;
  
  public SimulationController() {
    
  }

  @Override
  public void addStep(Step step) {
      if (!sessions.containsKey(step.getSessionID())) {
          System.out.println("this session is not part of the simulation");
          assert(false);
      }
      if (stepQueue.containsKey(step.getSessionID())) {
          System.out.println("step queue already contains a step from this session");
          assert(false);
      }
      System.out.println("addStep: "+step + " id: " + step.getSessionID() + " sequence: "+step.getSequenceNumber());
      stepQueue.put(step.getSessionID(), step);
  }
  
  /*
   * Register session
   */
  public void addSession(int sessionId, String name, GameEngineInterface gameEngine) {
      assert(!gameEngines.containsKey(sessionId));
      gameEngines.put(sessionId, gameEngine);
      sessions.put(sessionId, name);
      System.out.println("add session: "+name);
  }
  
  public void initSimulation() {
      System.out.println("init simulation");
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
      System.out.println("----------- simulateStep "+seqNum+" -----------");
      boolean advance = false;
      if (seqNum % (Config.advanceStepLimit-maxLevel) == 0) {
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
          System.out.println("step: "+s+" session: "+session);
          if (s == null) {
              System.out.println("tried to simulate step, but not all steps are available. Session "+session+ " is missing");
              assert(false);
          }
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
          if (g.getLevel() > maxLevel && maxLevel < Config.maxLevelForSpeed) {
                maxLevel = g.getLevel();
            }
      }
      assert(stepQueue.isEmpty());
      System.out.println("----------------------");
  }
  
  public SimulationStateInterface getSimulation(int sessionId) {
      assert(gameEngines.containsKey(sessionId));
      System.out.println("contains key: "+gameEngines.containsKey(sessionId));
      return gameEngines.get(sessionId);
  }
    
}
