package domain.actions;

/**
 *
 * @author Patrick Zenhäusern <pzenhaeu@hsr.ch>
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
