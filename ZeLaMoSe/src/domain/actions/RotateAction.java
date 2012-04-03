/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.actions;

/**
 *
 * @author chrigi
 */
public class RotateAction extends Action {
    
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
}
