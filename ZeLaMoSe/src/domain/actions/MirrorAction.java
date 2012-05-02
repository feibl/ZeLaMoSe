/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.actions;

/**
 *
 * @author Patrick Zenhäusern
 */
public class MirrorAction extends Action {
    private int blockNumber;
        public MirrorAction(long timestamp, int blockNumber) {
        super(ActionType.MIRROR, timestamp);
        this.blockNumber = blockNumber;
    }

    public int getBlockNumber() {
        return blockNumber;
    }

}
