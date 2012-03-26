/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.actions;

/**
 *
 * @author chrigi
 */
public class RmlineAction extends Action{
    private int numlines;
    private int offset;
    public RmlineAction(int timestamp, int numlines, int offset) {
        super(ActionType.RMLINE, timestamp);
        this.numlines = numlines;
        this.offset = offset;
    }
    
    public int getNumlines() {
        return numlines;
    }
    
    public int getOffset() {
        return offset;
    }
    
}
