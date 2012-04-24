/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.actions;

import java.io.Serializable;

/**
 *
 * @author Cyrill Lam <clam@hsr.ch>
 */
public class Action implements Serializable{
    
    ActionType type;
    
    //The timestamp is relative to the step, here in milliseconds
    long timeStamp = 0;
    
    public Action(ActionType type, java.lang.Long timestamp) {
        this.type = type;
        this.timeStamp = timestamp;
    }

    public ActionType getType() {
        return type;
    }
    
    public long getTimestamp() {
        return timeStamp;
    }
    
}
