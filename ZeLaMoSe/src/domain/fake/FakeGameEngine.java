/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.fake;
import domain.SimulationController;
import domain.GameEngineAbstract;
import domain.actions.Action;
import domain.actions.GarbageLineAction;
import domain.block.BlockAbstract;

/**
 *
 * @author Christian Mollekopf <cmolleko@hsr.ch>
 */
public class FakeGameEngine extends GameEngineAbstract {
  private Action lastAction  = null;
  private int sessionId;
  public FakeGameEngine(int sessionId) {
      this.sessionId = sessionId;
  }

  @Override
  public int getSessionID() {
    return this.sessionId;
  }

    @Override
    public void startGame() {
      
    }  
  
  @Override
  public void handleAction(Action action) {
//      System.out.println("simulate action");
      lastAction = action;
      setChanged();
      notifyObservers();
  }
  
  public Action getLastAction() {
      return lastAction;
  }

  @Override
  public Action getSimulationState() {
    //do nothing
      return lastAction;
  }

    @Override
    public int getLevel() {
        //do nothing
        return 0;
    }

    @Override
    public String toString() {
        return "MockGameEndine " + sessionId;
    }


    @Override
    public void setNickName(String nickName) {
       
    }

    @Override
    public GarbageLineAction getlastActionForOthers() {
       return null;
    }

    @Override
    public int getNumberOfJokers() {
       return 0;
    }

    @Override
    public int getScore() {
       return 0;
    }

    @Override
    public void setRank(int rank) {
        
    }

    @Override
    public int getRank() {
        return 0;
    }

    @Override
    public String getNickName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public BlockAbstract getNextBlock() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getTotalRemovedLines() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public BlockAbstract getCurrentBlock() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getBlockCounter() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setLevel(int currentHighestLevel) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

  
    
}
