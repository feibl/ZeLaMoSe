/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.actions;

import domain.Stone;

/**
 *
 * @author chrigi
 */
public class NewblockAction extends Action{

    Stone type;
    public NewblockAction(Stone type, int timestamp) {
        super(ActionType.NEWBLOCK, timestamp);
        this.type = type;
    }
    
    public Stone getBlocktype() {
        return type;
    }
    
    
}
