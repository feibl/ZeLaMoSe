/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.actions;

import java.util.List;

/**
 *
 * @author chrigi
 */
public class RmlineAction extends Action{
    private List<Integer> offset;
    public RmlineAction(long timestamp,List<Integer> linesToRemove) {
        super(ActionType.RMLINE, timestamp);
        this.offset = offset;
    }
    
    
    public List<Integer> getLinesToRemove() {
        return offset;
    }
    
    
}
