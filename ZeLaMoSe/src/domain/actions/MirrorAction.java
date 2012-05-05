/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.actions;

/**
 *
 * @author Patrick Zenhäusern
 */
public class MirrorAction extends ActionForOthersAbstract {

    public MirrorAction(long timestamp, int blockNumber) {
        super(timestamp, blockNumber, ActionType.MIRROR);
    }
}
