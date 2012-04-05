/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import domain.Step;
import network.client.Handler;


/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class AddStepRunnable implements Runnable {
   private final Step step;
   private final Handler handler;

   public AddStepRunnable(Step step, Handler handler) {
      this.step = step;
      this.handler = handler;
   }

   @Override
   public void run() {
      handler.sendStep(step);
   }
   
}
