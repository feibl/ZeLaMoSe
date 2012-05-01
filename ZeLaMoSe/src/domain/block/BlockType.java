/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.block;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Patrick Zenhäusern <pzenhaeu@hsr.ch>
 */
public enum BlockType {
    I(IBlock.class,100), J(JBlock.class,100), L(LBlock.class,100), 
    O(OBlock.class,100), S(SBlock.class,100), T(TBlock.class,100), 
    Z(ZBlock.class,100), GARBAGE(GarbageBlock.class,0), Mirror(MirrorBlock.class,100);
    
    private final Class className;
    private final int probability;
    public BlockAbstract createBlock() {
        try {
            return (BlockAbstract)className.newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(BlockType.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(BlockType.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    BlockType(Class cn, int probability){
        this.className = cn;
        this.probability = probability;
    }

    public int getProbability() {
        return probability;
    }
    
    
    
    
}
