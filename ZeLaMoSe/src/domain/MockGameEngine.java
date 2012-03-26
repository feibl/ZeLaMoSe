/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;
import domain.actions.Action;
import java.util.Observable;

/**
 *
 * @author chrigi
 */
public class MockGameEngine extends Observable implements GameEngineInterface {
  private Action lastAction;
  private int sessionId;
  public MockGameEngine(int sessionId) {
      this.sessionId = sessionId;
  }

  @Override
  public int sessionId() {
    return this.sessionId;
  }
    
  
  @Override
  public void simulateAction(Action action) {
      System.out.println("simulate action");
      lastAction = action;
  }
  
  public Action getLastAction() {
      return lastAction;
  }

  @Override
  public Action getSimulationState() {
    throw new UnsupportedOperationException("Not supported yet.");
  }
  
  
  
}
