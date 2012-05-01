/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.actions;

/**
 *
 * @author Patrick Zenhäusern
 */
public class MirrorAction extends Action {
        public MirrorAction(long timestamp) {
        super(ActionType.MIRROR, timestamp);
    }

}
