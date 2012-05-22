package domain.block;

import domain.block.wallkick.NoWallKick;
import java.awt.Color;

/**
 *
 * @author Patrick Zenhäusern <pzenhaeu@hsr.ch>
 */
public class OBlock extends BlockAbstract {

    public OBlock(int blockNumber, long seed) {
        super(new Color(255, 255, 0), "O", new NoWallKick(), blockNumber, seed);
    }

    @Override
    protected void rotation0() {
        grid[1][0] = this;
        grid[2][0] = this;
        grid[1][1] = this;
        grid[2][1] = this;
    }

    @Override
    protected void rotation90() {
        rotation0();
    }

    @Override
    protected void rotation180() {
        rotation0();
    }

    @Override
    protected void rotation270() {
        rotation0();
    }
}
