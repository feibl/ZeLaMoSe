/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.actions;

import java.util.List;

/**
 * 
 * @author Patrick Zenhäusern <pzenhaeu@hsr.ch>
 */
public class RemoveLineAction extends Action{
    
    private List<Integer> linesToRemove;
    /**
     * 
     * @param timestamp
     * @param linesToRemove: Has the lines to remove in it (top = 0, bottom = 23)
     */
    public RemoveLineAction(long timestamp,List<Integer> linesToRemove) {
        super(ActionType.REMOVELINE, timestamp);
        this.linesToRemove = linesToRemove;
    }
    
    
    public List<Integer> getLinesToRemove() {
        return linesToRemove;
    }
    
    
}
