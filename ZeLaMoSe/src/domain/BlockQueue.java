/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.Random;

/**
 *
 * @author Cyrill
 */
public class BlockQueue {
    
    private Random randomgenerator = new Random();
    
    
    public Stone getNextStone(){
//        return new Stone(StoneType.values()[randomgenerator.nextInt(StoneType.values().length)]);
        return new Stone(StoneType.I);
    }
    
    
}
