/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.block;

import domain.block.wallkick.NoWallKick;
import java.awt.Color;

/**
 *
 * @author Patrick Zenhäusern <pzenhaeu@hsr.ch>
 */
public class OBlock extends BlockAbstract {

    public OBlock(int blockNumber,long seed) {
        super(new Color(255, 255, 0), "O", new NoWallKick(),blockNumber,seed);
    }

    @Override
    protected void rotation0(BlockAbstract[][] grid) {
        grid[1][0] = this;
        grid[2][0] = this;
        grid[1][1] = this;
        grid[2][1] = this;
    }

    @Override
    protected void rotation90(BlockAbstract[][] grid) {
        rotation0(grid);
    }

    @Override
    protected void rotation180(BlockAbstract[][] grid) {
        rotation0(grid);
    }

    @Override
    protected void rotation270(BlockAbstract[][] grid) {
        rotation0(grid);
    }
}
