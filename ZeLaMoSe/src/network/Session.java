/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class Session {
  private final int id;
  private final String nickname;
  public Session(int id, String nickname) {
    this.id = id;
    this.nickname = nickname;
  }

  /**
   * @return the nickname
   */
  public String getNickname() {
    return nickname;
  }

  /**
   * @return the id
   */
  public int getId() {
    return id;
  }
}
