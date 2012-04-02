/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.actions.InputEvent;
import domain.actions.InputEvent;
import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * This class is the "KeyListener" of the GUIs he gets the events and notifys the StepGenerator
 *
 * @author Patrick Zenhäusern
 */
public class InputSampler implements KeyEventDispatcher {

   private ConcurrentLinkedQueue<InputEvent> keyEventQueue;

   public InputSampler() {
      keyEventQueue = new ConcurrentLinkedQueue<InputEvent>();
   }

   @Override
   public boolean dispatchKeyEvent(KeyEvent e) {
//        if (e.getID() == KeyEvent.KEY_TYPED) {
      if (e.getID() == KeyEvent.KEY_PRESSED) {
         keyEventQueue.add(new InputEvent(e, System.nanoTime()));
      }
      //Allow the event to be redispatched
      return false;
   }

   public Collection<InputEvent> getAndRemoveAllFromQueue() {
      Collection<InputEvent> c = new ArrayList<InputEvent>(keyEventQueue);
      keyEventQueue.removeAll(c);
      return c;
   }
}
