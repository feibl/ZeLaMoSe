/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.actions;

/**
 *
 * @author chrigi
 */
public class MoveAction extends Action {
    
    public enum Direction {
        LEFT, RIGHT, DOWN
    }
    private Direction dir;
    private int speed;

    public MoveAction(long timestamp, Direction dir, int speed) {
        super(ActionType.MOVE, timestamp);
        this.dir = dir;
        this.speed = speed;
    }
    
    public Direction getDirection() {
        return dir;
    }
    
    public int getSpeed() {
        return speed;
    }
    
    
}
