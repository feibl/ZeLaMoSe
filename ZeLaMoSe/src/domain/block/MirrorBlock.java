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

    public MirrorBlock() {
        super(Color.black, "M", new NoWallKick());
    }
    
    @Override
    protected void rotation0(BlockAbstract[][] grid) {
        grid[0][0] = this;
    }

    @Override
    protected void rotation90(BlockAbstract[][] grid) {
        grid[1][1] = this;
    }

    @Override
    protected void rotation180(BlockAbstract[][] grid) {
        grid[2][2] = this;
    }

    @Override
    protected void rotation270(BlockAbstract[][] grid) {
       grid[3][3] = this;
    }
    
}
