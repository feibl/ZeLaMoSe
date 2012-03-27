/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.stone.StoneI;
import domain.stone.Stone;
import java.util.Random;

/**
 *
 * @author Cyrill
 */
public class BlockQueue {
    
    private Random randomgenerator = new Random(System.currentTimeMillis());
    
    
    public Stone nextBlock(){
        return new StoneI();
    }
    
    
}
