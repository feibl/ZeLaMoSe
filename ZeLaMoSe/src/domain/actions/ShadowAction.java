package domain.actions;

/**
 *
 * @author Patrick Zenhäusern <pzenhaeu@hsr.ch>
 */
public class ShadowAction extends ActionForOthersAbstract {

    public ShadowAction(long timestamp, int blockNumber) {
        super(timestamp, blockNumber, ActionType.DARK);
    }
}
