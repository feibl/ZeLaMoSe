/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import domain.Step;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public interface Handler {
  public void disconnect();
   public void sendChatMessage(String message);
   public void sendStep(Step step);
}
