/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.actions;

import domain.block.Block;

/**
 *
 * @author chrigi
 */
public class NewblockAction extends Action{

    Block type;
    public NewblockAction(Block type, long timestamp) {
        super(ActionType.NEWBLOCK, timestamp);
        this.type = type;
    }
    
    public Block getBlocktype() {
        return type;
    }
    
    
}
