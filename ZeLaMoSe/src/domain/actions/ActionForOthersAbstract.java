/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.actions;

/**
 *
 * @author Patrick Zenhäusern
 */
public abstract class ActionForOthersAbstract extends Action {

    private int blockNumber;

    public ActionForOthersAbstract(long timestamp, int blockNumber, ActionType actionType) {
        super(actionType, timestamp);
        this.blockNumber = blockNumber;
    }

    public int getBlockNumber() {
        return blockNumber;
    }
}
