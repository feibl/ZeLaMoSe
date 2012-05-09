/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network.server;

import network.GameParams;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public interface GameServerInterface {
   public void startGame(long blockQueueSeed, int nbrOfJokers, boolean includeSpecialBlocks, int startLevel);
}
