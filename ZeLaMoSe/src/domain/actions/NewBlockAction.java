/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.actions;

import domain.block.BlockAbstract;

/**
 *
 * @author chrigi
 */
public class NewBlockAction extends Action{

    BlockAbstract blockType;
    public NewBlockAction(BlockAbstract type, long timestamp) {
        super(ActionType.NEWBLOCK, timestamp);
        this.blockType = type;
    }
    
    public BlockAbstract getBlocktype() {
        return blockType;
    }
    
    
}
