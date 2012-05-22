package domain.actions;

/**
 *
 * @author Patrick Zenhäusern <pzenhaeu@hsr.ch>
 */
public class ClearAction extends Action {

    public ClearAction(long timestamp) {
        super(ActionType.CLEAR, timestamp);
    }
}
