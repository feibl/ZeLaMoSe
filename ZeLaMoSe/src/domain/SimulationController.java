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
import java.util.*;

/**
 *
 * @author chrigi
 */
public class SimulationController implements StepInterface {
  private Map<Integer, Step> stepQueue = new HashMap<Integer, Step>();
  protected Map<Integer, GameEngineInterface> gameEngines = new HashMap<Integer, GameEngineInterface>();
  protected Map<Integer, String> sessions = new HashMap<Integer, String>();
  protected int maxLevel = 1;
  public boolean autoadvance = true;
  
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
//      System.out.println("addStep: "+step + " id: " + step.getSessionID() + " sequence: "+step.getSequenceNumber());
      stepQueue.put(step.getSessionID(), step);
  }
  
  /*
   * Register session
   */
  public void addSession(int sessionId, String name, GameEngineInterface gameEngine) {
      assert(!gameEngines.containsKey(sessionId));
      gameEngines.put(sessionId, gameEngine);
      sessions.put(sessionId, name);
//      System.out.println("add session: "+name);
  }
  
  public void initSimulation() {
//      System.out.println("init simulation");
      for (GameEngineInterface e: gameEngines.values()) {
          e.startGame();
      }
  }
  
  
  
  /*
   * This is just a workaround because the map doesn't seem to sort as expected, at least not with the entry set.
   */
  protected ArrayList < Map.Entry<Action, Integer>> sortEntrySet( Set< Map.Entry<Action, Integer> > set) {
      ArrayList< Map.Entry<Action, Integer>> sortedList = new ArrayList< Map.Entry<Action, Integer>>();
      for (Map.Entry<Action, Integer> e: set) {
          int i = 0;
          for(Map.Entry<Action, Integer> e1: sortedList) {
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
   * Execute simulation step.
   * - Look for necessary states
   * - Sort Actions
   * - Simulation ACtions
   */
  public void simulateStep(int seqNum) {
      System.out.println("----------- simulateStep "+seqNum+" -----------");
      boolean advance = false;
      if (autoadvance && (seqNum % (Config.advanceStepLimit-maxLevel) == 0)) {
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
          //System.out.println("step: "+s+" session: "+session);
          if (s == null) {
              System.out.println("tried to simulate step, but not all steps are available. Session "+session+ " is missing");
              assert(false);
          }
          if (s.getSequenceNumber() != seqNum) {
              //throw new Exception("Invalid sequenceNumber"+s.getSequenceNumber());
              assert(false);
          }
          assert(s.getSessionID() == session);
          for (Action a: s.getActions()) {
              actionList.put(a, session);
//              System.out.println("adding actions: " + a + " for session "+ session);
          }
      }
      assert(stepQueue.isEmpty());
//      System.out.println("simulate actions: "+actionList.entrySet().size());
      if (advance) {
          for (GameEngineInterface g: gameEngines.values()) {
              g.handleAction(new MoveAction(0, MoveAction.Direction.DOWN, 1));
          }
      }
      for (Map.Entry<Action, Integer> e: sortEntrySet(actionList.entrySet())) {
          System.out.println("--Simulating action with timestamp: "+e.getKey().getTimestamp()+" sessionid " +e.getValue());
          assert (gameEngines.containsKey(e.getValue()));
          GameEngineInterface g = gameEngines.get(e.getValue());
          assert (g.getSessionID() == e.getValue());
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
