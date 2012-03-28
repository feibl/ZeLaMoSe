/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.actions;

/**
 *
 * @author chrigi
 */
public class NewlineAction extends Action{
    boolean[][] line;

    public NewlineAction(long timestamp, boolean[][] line) {
        super(ActionType.NEWLINE, timestamp);
        this.line = line;
    }
    
    public boolean[][] getLine() {
        return line;
    }
    
}
