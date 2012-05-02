/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.actions;

/**
 *
 * @author Patrick Zenhäusern
 */
public class ClearAction extends Action {
        public ClearAction(long timestamp) {
        super(ActionType.CLEAR, timestamp);
    }
}
