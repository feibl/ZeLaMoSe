/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class SendChatMessageRunnable implements Runnable {
   private final String message;
   private final Handler handler;

   public SendChatMessageRunnable(String Message, Handler handler) {
      this.message = Message;
      this.handler = handler;
   }
   
   @Override
   public void run() {
      handler.sendChatMessage(message);
   }
   
}
