/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.actions;

/**
 *
 * @author Cyrill
 */
public class Action {
    
    ActionType type;
    
    //The timestamp is relative to the step, here in milliseconds
    long ts = 0;
    
    public Action(ActionType type, java.lang.Long timestamp) {
        this.type = type;
        this.ts = timestamp;
    }

    public ActionType getType() {
        return type;
    }
    
    public long getTimestamp() {
        return ts;
    }
    
}
