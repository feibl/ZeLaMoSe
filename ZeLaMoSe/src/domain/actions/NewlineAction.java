/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.actions;

enum LineDefinition {
    LINE1,
    LINE2
}

/**
 *
 * @author chrigi
 */
public class NewlineAction extends Action{
    LineDefinition line;

    public NewlineAction(int timestamp, LineDefinition line) {
        super(ActionType.NEWLINE, timestamp);
        this.line = line;
    }
    
    public LineDefinition getLine() {
        return line;
    }
    
}
