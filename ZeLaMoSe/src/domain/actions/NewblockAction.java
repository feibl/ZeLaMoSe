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
public class NewBlockAction extends Action{

    Block type;
    public NewBlockAction(Block type, long timestamp) {
        super(ActionType.NEWBLOCK, timestamp);
        this.type = type;
    }
    
    public Block getBlocktype() {
        return type;
    }
    
    
}
