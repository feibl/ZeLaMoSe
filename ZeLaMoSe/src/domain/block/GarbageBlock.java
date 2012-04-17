/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.block;

import domain.block.wallkick.NoWallKick;
import domain.block.wallkick.WallKick;
import java.awt.Color;

/**
 *
 * @author Cyrill
 */
public class GarbageBlock extends Block {

    public GarbageBlock() {
        super(Color.GRAY, "G", new NoWallKick());
    }
    
    

    @Override
    protected void rotation0(Block[][] grid) {
        grid[0][0] = this;
    }

    @Override
    protected void rotation90(Block[][] grid) {
        throw new UnsupportedOperationException("Will never be Supported with this kind of Block");
    }

    @Override
    protected void rotation180(Block[][] grid) {
        throw new UnsupportedOperationException("Will never be Supported with this kind of Block");
    }

    @Override
    protected void rotation270(Block[][] grid) {
        throw new UnsupportedOperationException("Will never be Supported with this kind of Block");
    }
    
}
