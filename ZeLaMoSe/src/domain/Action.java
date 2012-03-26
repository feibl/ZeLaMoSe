/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.Date;

/**
 *
 * @author Cyrill
 */
public class Action {
    
    ActionType type;
    //to be replaced by ts
    Date timestamp;
    
    //The timestamp is relative to the step, here in milliseconds
    int ts = 0;

    public Action(ActionType type, Date timestamp) {
        this.type = type;
        this.timestamp = timestamp;
    }
    
    public Action(ActionType type, int timestamp) {
        this.type = type;
        this.ts = timestamp;
    }

    public ActionType getType() {
        return type;
    }
    
    public int getTimestamp() {
        return ts;
    }
    
    
    
}
