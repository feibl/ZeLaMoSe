/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.block;

import java.lang.reflect.Constructor;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Patrick Zenhäusern <pzenhaeu@hsr.ch>
 */
public enum BlockType {
    I(IBlock.class,100), J(JBlock.class,100), L(LBlock.class,100), 
    O(OBlock.class,100), S(SBlock.class,100), T(TBlock.class,100), 
    Z(ZBlock.class,100), GARBAGE(GarbageBlock.class,0), MIRROR(MirrorBlock.class,10),  
    DARK(DarkBlock.class,10);
    
    private final Class className;
    private final int probability;
    public BlockAbstract createBlock(int blockNumber) {
        try {
            Constructor c = className.getConstructor(new Class[]{Integer.TYPE});
            
            return (BlockAbstract)(c.newInstance(new Object[]{blockNumber}));
        }catch (Exception ex) {
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
