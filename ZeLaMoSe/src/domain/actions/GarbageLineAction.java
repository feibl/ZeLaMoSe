/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.actions;

import domain.block.Block;

/**
 *
 * @author chrigi
 */
public class GarbageLineAction extends Action{
    Block[][] lines;

    public GarbageLineAction(long timestamp, Block[][] line) {
        super(ActionType.NEWLINE, timestamp);
        this.lines = line;
    }

    
    public Block[][] getLines() {
        return lines;
    }
    
}
