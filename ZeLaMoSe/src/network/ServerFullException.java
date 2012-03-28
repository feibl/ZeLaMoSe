/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class ServerFullException extends Exception {

   @Override
   public String getMessage() {
      return "Connection Refused: Server ist Full";
   }
   
}
