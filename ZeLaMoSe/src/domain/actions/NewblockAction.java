/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.actions;

import domain.StoneType;

/**
 *
 * @author chrigi
 */
public class NewblockAction extends Action{

    StoneType type;
    public NewblockAction(StoneType type, int timestamp) {
        super(ActionType.NEWBLOCK, timestamp);
        this.type = type;
    }
    
    public StoneType getBlocktype() {
        return type;
    }
    
    
}
