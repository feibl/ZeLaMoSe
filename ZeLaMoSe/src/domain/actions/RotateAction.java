package domain.actions;

/**
 *
 * @author Christian Mollekopf <cmolleko@hsr.ch>
 */
public class RotateAction extends Action {

    private int XOffset, YOffset;

    public void setXOffset(int XOffset) {
        this.XOffset = XOffset;
    }

    public void setYOffset(int YOffset) {
        this.YOffset = YOffset;
    }

    public enum Direction {

        LEFT, RIGHT
    }
    private Direction direction;

    public RotateAction(long timestamp, Direction dir) {
        super(ActionType.ROTATION, timestamp);
        this.direction = dir;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getXOffset() {
        return XOffset;
    }

    public int getYOffset() {
        return YOffset;
    }
}
