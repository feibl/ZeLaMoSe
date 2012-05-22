package domain.actions;

/**
 *
 * @author Patrick Zenhäusern <pzenhaeu@hsr.ch>
 */
public class MirrorAction extends ActionForOthersAbstract {

    public MirrorAction(long timestamp, int blockNumber) {
        super(timestamp, blockNumber, ActionType.MIRROR);
    }
}
