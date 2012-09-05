/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.actions;

/**
 *
 * @author feibl
 */
public class RestartCountdownAction extends Action {

    public int remainingTime;

    public RestartCountdownAction(long timestamp, int remainingTime) {
        super(ActionType.RESTART_COUNTDOWN, timestamp);
        this.remainingTime = remainingTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }
}
