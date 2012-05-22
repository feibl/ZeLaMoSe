package domain.actions;

/**
 *
 * @author Patrick Zenhäusern <pzenhaeu@hsr.ch>
 */
public class GameOverAction extends Action {

    public GameOverAction(long timestamp) {
        super(ActionType.GAMEOVER, timestamp);
    }
}
