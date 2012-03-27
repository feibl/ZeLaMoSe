/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.block.BlockI;
import domain.block.Block;
import java.util.Random;

/**
 *
 * @author Cyrill
 */
public class BlockQueue {
    
    private Random randomgenerator = new Random(System.currentTimeMillis());
    
    

    public Block getNextBlock(){
        return new BlockI();
    }
    
    
}
