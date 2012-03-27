/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.actions.Action;
import domain.actions.InputEvent;
import domain.actions.MoveAction;
import domain.actions.MoveAction.Direction;
import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Queue;

/**
 *
 * This class is the "KeyListener" of the GUIs he gets the events and notifys the StepGenerator
 * @author Patrick Zenhäusern
 */
public class InputSampler extends Observable implements KeyEventDispatcher {

    private Queue<InputEvent> keyEventQueue;

    public InputSampler() {
        keyEventQueue = new LinkedList<InputEvent>();
    }
    
    
    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        if (e.getID() == KeyEvent.KEY_TYPED) {
            keyEventQueue.add(new InputEvent(e,System.nanoTime()));
        }
        //Allow the event to be redispatched
        return false;
    }
    
    
    
}
