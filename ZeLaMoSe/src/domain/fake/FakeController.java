/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.Fake;

import domain.GameEngine;
import domain.StepGenerator;
import domain.actions.Action;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cyrill
 */
public class FakeController implements Observer{

    GameEngine ge;
    StepGenerator sg;
    public FakeController(GameEngine ge, final StepGenerator sg) {
        this.ge = ge;
        this.sg = sg;
        
        sg.addObserver(this);
        new Thread(new Runnable() {

            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FakeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    sg.niggasInParis();
                }
            }
        }).start();
    }
    
    

    @Override
    public void update(Observable o, Object o1) {
        for(Action a:sg.getStep().getActions()){
            ge.handleAction(a);
        }
    }
    
    
    
}
