/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.block.Block;
import domain.block.BlockType;
import java.util.Random;

/**
 *
 * @author Cyrill
 */
public class BlockQueue {
    
    private Random randomGenerator;

    /**
     * initialise blockqueue with current SystemTime as Seed 
     */
    public BlockQueue() {
        randomGenerator = new Random(System.currentTimeMillis());
    }
    
    /**
     * initalise blockqueue with given seed to make sure it's always the order
     * @param seed 
     * 
     */
    public BlockQueue(long seed){
        randomGenerator = new Random(seed);
    }
    
    

    public Block getNextBlock(){
        return (BlockType.values()[randomGenerator.nextInt(BlockType.values().length)]).createBlock();
    }
    
    
}
