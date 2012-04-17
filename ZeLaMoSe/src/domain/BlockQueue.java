/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.interfaces.BlockQueueInterface;
import domain.block.Block;
import domain.block.BlockType;
import domain.block.GarbageBlock;
import java.util.Random;

/**
 *
 * @author Cyrill
 */
public class BlockQueue implements BlockQueueInterface {

    private Random randomGenerator;

    /**
     * initalise blockqueue with given seed to make sure it's always the same order
     *
     * @param seed
     *
     */
    public BlockQueue(long seed) {
        randomGenerator = new Random(seed);
    }

    public Block getNextBlock() {
        Block returnValue = null;
        do {
            returnValue = (BlockType.values()[randomGenerator.nextInt(BlockType.values().length)]).createBlock();
        } while (returnValue instanceof GarbageBlock);
        return returnValue;
    }
}
