/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class SessionAddedMessage implements NetworkMessage {

   SessionInformation sessionInformation;

   public SessionAddedMessage(SessionInformation sessionInformation) {
      this.sessionInformation = sessionInformation;
   }

   @Override
   public Object getMessageObject() {
      return sessionInformation;
   }
}
