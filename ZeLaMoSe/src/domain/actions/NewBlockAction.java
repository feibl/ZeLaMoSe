package domain.actions;

import domain.block.BlockAbstract;

/**
 *
 * @author Christian Mollekopf <cmolleko@hsr.ch>
 */
public class NewBlockAction extends Action {

    BlockAbstract blockType;

    public NewBlockAction(BlockAbstract type, long timestamp) {
        super(ActionType.NEWBLOCK, timestamp);
        this.blockType = type;
    }

    public BlockAbstract getBlocktype() {
        return blockType;
    }
}
