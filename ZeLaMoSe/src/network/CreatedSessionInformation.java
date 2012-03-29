/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class CreatedSessionInformation {
   private SessionInformation sessionInformation;
   private ServerRemote serverRemote;

   public CreatedSessionInformation(SessionInformation sessionInformation, ServerRemote serverRemote) {
      this.sessionInformation = sessionInformation;
      this.serverRemote = serverRemote;
   }

   /**
    * @return the sessionInformation
    */
   public SessionInformation getSessionInformation() {
      return sessionInformation;
   }

   /**
    * @return the serverRemote
    */
   public ServerRemote getServerRemote() {
      return serverRemote;
   }
}
