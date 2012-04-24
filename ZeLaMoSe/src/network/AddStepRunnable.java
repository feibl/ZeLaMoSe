/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import domain.Step;
import network.client.HandlerInterface;


/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class AddStepRunnable implements Runnable {
   private final Step step;
   private final HandlerInterface handler;

   public AddStepRunnable(Step step, HandlerInterface handler) {
      this.step = step;
      this.handler = handler;
   }

   @Override
   public void run() {
      handler.sendStep(step);
   }
   
}
