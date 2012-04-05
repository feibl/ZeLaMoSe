/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import network.client.Handler;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class DisconnectionRunnable implements Runnable {
   private final Handler handler;

   public DisconnectionRunnable(Handler handler) {
      this.handler = handler;
   }

   @Override
   public void run() {
      handler.disconnect();
   }
   
}
