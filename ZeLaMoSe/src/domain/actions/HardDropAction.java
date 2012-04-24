/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.actions;

/**
 *
 * @author Christian Mollekopf <cmolleko@hsr.ch>
 */
public class HardDropAction extends Action {

    public HardDropAction(long timestamp) {
        super(ActionType.HARDDROP, timestamp);
    }
    
}
