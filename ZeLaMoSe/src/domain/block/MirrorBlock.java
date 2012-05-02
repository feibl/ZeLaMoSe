/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.block;

import domain.block.wallkick.NoWallKick;
import domain.block.wallkick.WallKickAbstract;
import java.awt.Color;

/**
 *
 * @author Cyrill Lam <clam@hsr.ch>
 */
public class MirrorBlock extends BlockAbstract {

    public MirrorBlock(int blockNumber) {
        super(Color.white, "M", new NoWallKick(),blockNumber);
    }
    
    @Override
    protected void rotation0(BlockAbstract[][] grid) {
        grid[0][0] = this;
        grid[2][0] = this;
        grid[2][2] = this;
        grid[0][2] = this;
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
