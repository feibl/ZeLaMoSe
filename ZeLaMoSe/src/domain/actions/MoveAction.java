package domain.actions;

/**
 *
 * @author Christian Mollekopf <cmolleko@hsr.ch>
 */
public class MoveAction extends Action {

    public enum Direction {

        LEFT, RIGHT, DOWN
    }
    private Direction direction;
    private int speed;

    public MoveAction(long timestamp, Direction dir, int speed) {
        super(ActionType.MOVE, timestamp);
        this.direction = dir;
        this.speed = speed;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getSpeed() {
        return speed;
    }
}
