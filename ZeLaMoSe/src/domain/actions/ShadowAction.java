/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.actions;

/**
 *
 * @author Patrick Zenhäusern
 */
public class ShadowAction extends ActionForOthersAbstract {

    public ShadowAction(long timestamp, int blockNumber) {
        super(timestamp, blockNumber, ActionType.DARK);
    }
}
