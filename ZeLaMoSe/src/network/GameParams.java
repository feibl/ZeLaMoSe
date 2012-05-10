/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.io.Serializable;

/**
 *
 * @author feibl
 */
public class GameParams implements Serializable{
    private final long blockQueueSeed;
    private final int nbrOfJokers;
    private final boolean includeSpecialBlocks;
    private final int startLevel;

    public GameParams(long blockQueueSeed, int nbrOfJokers, boolean includeSpecialBlocks, int startLevel) {
        this.blockQueueSeed = blockQueueSeed;
        this.nbrOfJokers = nbrOfJokers;
        this.includeSpecialBlocks = includeSpecialBlocks;
        this.startLevel = startLevel;
    }

    public long getBlockQueueSeed() {
        return blockQueueSeed;
    }

    public boolean isIncludeSpecialBlocks() {
        return includeSpecialBlocks;
    }

    public int getNbrOfJokers() {
        return nbrOfJokers;
    }

    public int getStartLevel() {
        return startLevel;
    }
    
}
