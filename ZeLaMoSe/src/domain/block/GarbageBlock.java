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
public class GarbageBlock extends BlockAbstract {

    public GarbageBlock(int blockNumber,long seed) {
        super(Color.GRAY, "G", new NoWallKick(),blockNumber,seed);
    }
    
    

    @Override
    protected void rotation0(BlockAbstract[][] grid) {
        grid[0][0] = this;
    }

    @Override
    protected void rotation90(BlockAbstract[][] grid) {
        throw new UnsupportedOperationException("Will never be Supported with this kind of Block");
    }

    @Override
    protected void rotation180(BlockAbstract[][] grid) {
        throw new UnsupportedOperationException("Will never be Supported with this kind of Block");
    }

    @Override
    protected void rotation270(BlockAbstract[][] grid) {
        throw new UnsupportedOperationException("Will never be Supported with this kind of Block");
    }
    
}
