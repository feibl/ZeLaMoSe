/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networkTest;

import domain.Step;
import network.*;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class NetworkHandlerImplWithoutThreads extends NetworkHandlerImpl{

   @Override
   public void connectToServer(String ip, String serverName, String nickname) {
      new ConnectionRunnable(this, ip, serverName, nickname).run();
   }

   @Override
   public void disconnectFromServer() {
      new DisconnectionRunnable(super.getHandler()).run();
   }

   @Override
   public void sendChatMessage(String message) {
      new SendChatMessageRunnable(message, super.getHandler()).run();
   }

   @Override
   public void addStep(Step step) {
      new AddStepRunnable(step, super.getHandler()).run();
   }
   
}
