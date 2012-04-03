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
 * @author Patrick Zenhäusern
 */
public enum BlockType {
    I(IBlock.class), J(JBlock.class), L(LBlock.class), 
    O(OBlock.class), S(SBlock.class), T(TBlock.class), 
    Z(ZBlock.class);
    
    private final Class className;

    public Block createBlock() {
        try {
            return (Block)className.newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(BlockType.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(BlockType.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    BlockType(Class cn){
        className = cn;
    }
    
    
}
