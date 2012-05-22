package domain.block;

import domain.block.wallkick.NoWallKick;
import java.awt.Color;

/**
 *
 * @author Cyrill Lam <clam@hsr.ch>
 */
public class DarkBlock extends BlockAbstract implements SpecialBlockInterface {

    public DarkBlock(int blockNumber, long seed) {
        super(new Color(40, 40, 40), "S", new NoWallKick(), blockNumber, seed);
    }

    @Override
    protected void rotation0() {
        grid[1][0] = this;
        grid[2][0] = this;
        grid[1][1] = this;
        grid[3][1] = this;
        grid[1][2] = this;
        grid[3][2] = this;
        grid[1][3] = this;
        grid[2][3] = this;
    }

    @Override
    protected void rotation90() {
        grid[1][0] = this;
        grid[2][0] = this;
        grid[3][0] = this;
        grid[1][1] = this;
        grid[1][2] = this;
        grid[3][1] = this;
        grid[1][2] = this;
        grid[2][2] = this;
        grid[3][2] = this;
        grid[1][3] = this;
        grid[3][3] = this;
    }

    @Override
    protected void rotation180() {
        grid[1][0] = this;
        grid[2][0] = this;
        grid[3][0] = this;
        grid[1][1] = this;
        grid[3][1] = this;
        grid[1][2] = this;
        grid[2][2] = this;
        grid[1][3] = this;
        grid[3][3] = this;
    }

    @Override
    protected void rotation270() {
        grid[1][0] = this;
        grid[3][0] = this;
        grid[1][1] = this;
        grid[2][1] = this;
        grid[1][2] = this;
        grid[2][2] = this;
        grid[1][3] = this;
        grid[3][3] = this;
    }
}
