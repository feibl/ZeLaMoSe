/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.Serializable;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class ChatMessage implements Serializable {
   private final SessionInformation sender;
   private final String message;

   public ChatMessage(SessionInformation sender, String Message) {
      this.sender = sender;
      this.message = Message;
   }

   public String getMessage() {
      return message;
   }

   public SessionInformation getSender() {
      return sender;
   }
   
   
}
