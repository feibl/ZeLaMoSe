/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import domain.Step;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public interface Handler {
  public void disconnect();
   public void addChatMessage(String message);
   public void addStep(Step step);
   public List<SessionInformation> getOtherSessions();
   public void setRemote(ServerRemote serverRemote);
}
