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
    Date timestamp;

    public Action(ActionType type, Date timestamp) {
        this.type = type;
        this.timestamp = timestamp;
    }

    public ActionType getType() {
        return type;
    }
    
    
    
}
