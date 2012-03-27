/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.actions.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cyrill
 */
public class FakeGameEngine extends Observable{
    int FAKE_ACTIONS_TO_GENERATE = 150;
    ArrayList<Action> actionHistory = new ArrayList<Action>();
    BlockQueue queue = new BlockQueue();
    
    public FakeGameEngine(){
        new Thread(new Runnable() {

            @Override
            public void run() {
                Random randomGenerator = new Random(System.currentTimeMillis());
                
                //generates 50 random Actions
                for(int i=0;i<FAKE_ACTIONS_TO_GENERATE;i++){
                    int randomActionValue = randomGenerator.nextInt(ActionType.values().length);
                     Action action = null;
                     switch(ActionType.values()[randomActionValue]){
                         case HARDDROP:
                             action = new HarddropAction(0);
                             break;
                         case MOVE:
                             action = new MoveAction(0, MoveAction.Direction.values()[randomGenerator.nextInt(3)], 0);
                             break;
                         case ROTATION:
                             action = new RotateAction(0, RotateAction.Direction.values()[randomGenerator.nextInt(2)]);
                             break;
                         case NEWBLOCK:
                             action = new NewblockAction(queue.getNextStone(),0);
                            break;
                         default:
                             action = new RotateAction(0, RotateAction.Direction.values()[randomGenerator.nextInt(2)]);
                             break;
                     }
                    addNewAction(action);
                    try {
                        Thread.sleep(500+randomGenerator.nextInt(900));
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FakeGameEngine.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();
    }
    
    public void addNewAction(Action action){
        actionHistory.add(action);
        setChanged();
        notifyObservers();
    }
    
    
    public Action getLastAction(){
        return actionHistory.get(actionHistory.size()-1);
    }
    
    
}
