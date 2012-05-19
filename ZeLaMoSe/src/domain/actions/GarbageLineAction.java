/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.actions;

import domain.block.BlockAbstract;

/**
 *
 * @author Christian Mollekopf <cmolleko@hsr.ch>
 */
public class GarbageLineAction extends Action{
    BlockAbstract[][] lines;
    int currentBlockYOffset;

    public GarbageLineAction(long timestamp, BlockAbstract[][] line) {
        super(ActionType.GARBAGELINE, timestamp);
        this.lines = line;
    }

    
    public BlockAbstract[][] getLines() {
        return lines;
    }

    public void setYOffsetForCurrentBlock(int i) {
        currentBlockYOffset = i;
    }
    
    public int getYOffsetForCurrentBlock() {
        return currentBlockYOffset;
    }
    
}
