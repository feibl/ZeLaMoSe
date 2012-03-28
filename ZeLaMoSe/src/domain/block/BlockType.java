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
    BlockI(BlockI.class), BlockJ(BlockJ.class), BlockL(BlockL.class), 
    BlockO(BlockO.class), BlockS(BlockS.class), BlockT(BlockT.class), 
    BlockZ(BlockZ.class);
    
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
