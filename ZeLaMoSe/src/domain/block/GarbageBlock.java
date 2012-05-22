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
    protected void rotation0() {
        grid[0][0] = this;
    }

    @Override
    protected void rotation90() {
        throw new UnsupportedOperationException("Will never be Supported with this kind of Block");
    }

    @Override
    protected void rotation180() {
        throw new UnsupportedOperationException("Will never be Supported with this kind of Block");
    }

    @Override
    protected void rotation270() {
        throw new UnsupportedOperationException("Will never be Supported with this kind of Block");
    }
    
}
