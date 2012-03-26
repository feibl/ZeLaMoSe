/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.actions.Action;
import domain.actions.ActionType;
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
    int FAKE_ACTIONS_TO_GENERATE = 50;
    ArrayList<Action> actionHistory = new ArrayList<Action>();
    
    public FakeGameEngine(){
        new Thread(new Runnable() {

            @Override
            public void run() {
                Random randomGenerator = new Random();
                
                //generates 50 random Actions
                for(int i=0;i<FAKE_ACTIONS_TO_GENERATE;i++){
                    int randomActionValue = randomGenerator.nextInt(ActionType.values().length);
                     Action action = new Action(ActionType.values()[randomActionValue], null);
                    addNewAction(action);
                    try {
                        Thread.sleep(100+randomGenerator.nextInt(900));
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
