/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.actions.HarddropAction;
import domain.actions.InputEvent;
import domain.actions.MoveAction;
import domain.actions.RotateAction;
import java.awt.event.KeyEvent;
import java.util.Observable;
import network.NetworkHandler;

/**
 *
 * @author Patrick Zenhäusern
 * 
 * 
 */
public class StepGenerator extends Observable implements StepProducerInterface {
    

   private InputSampler inputsampler;
   private Step step;
   private int counter = 0;
   private int sessionID;

    public StepGenerator(InputSampler inputsampler,int sessionID) {
        this.inputsampler = inputsampler;
        this.sessionID = sessionID;
    }    
    
    @Override
    public void runStep(){
        step = new Step(counter++,sessionID);
        long currentTime = System.nanoTime();
        for(InputEvent ie : inputsampler.getAndRemoveAllFromQueue()){
            long relativeTime = (currentTime-ie.getAbsoluteTimeStamp());
            switch(ie.getKeyEvent()){
                case KeyEvent.VK_LEFT:
                    step.addAction(new MoveAction(relativeTime, MoveAction.Direction.LEFT, 1));
                    break;
                case KeyEvent.VK_RIGHT:
                    step.addAction(new MoveAction(relativeTime, MoveAction.Direction.RIGHT, 1));
                    break;
                case KeyEvent.VK_DOWN:
                    step.addAction(new MoveAction(relativeTime, MoveAction.Direction.DOWN, 1));
                    break;
                case KeyEvent.VK_Y:
                    step.addAction(new RotateAction(relativeTime, RotateAction.Direction.LEFT));
                    break;
                case KeyEvent.VK_X:
                    step.addAction(new RotateAction(relativeTime, RotateAction.Direction.RIGHT));
                    break;
                case KeyEvent.VK_SPACE:
                case KeyEvent.VK_UP:
                    step.addAction(new HarddropAction(relativeTime));
                    break;
            }
        }
        setChanged();
        notifyObservers(NetworkHandler.UpdateType.STEP);
    }

    @Override
    public Step getStep() {
        return step;
    }


}
