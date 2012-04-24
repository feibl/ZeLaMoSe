/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.block;

import domain.block.wallkick.GeneralWallKick;
import java.awt.Color;

/**
 *
 * @author Patrick Zenhäusern
 */
public class TBlock extends BlockAbstract {

    public TBlock() {
        super(new Color(153,0,255),"T", new GeneralWallKick());
    }

    @Override
    protected void rotation0(BlockAbstract[][] grid) {
        grid[1][0] = this;
        grid[0][1] = this;
        grid[1][1] = this;
        grid[2][1] = this;
    }

    @Override
    protected void rotation90(BlockAbstract[][] grid) {
        grid[1][0] = this;
        grid[1][1] = this;
        grid[2][1] = this;
        grid[1][2] = this;
    }

    @Override
    protected void rotation180(BlockAbstract[][] grid) {
        grid[0][1] = this;
        grid[1][1] = this;
        grid[2][1] = this;
        grid[1][2] = this;
    }

    @Override
    protected void rotation270(BlockAbstract[][] grid) {
        grid[1][0] = this;
        grid[0][1] = this;
        grid[1][1] = this;
        grid[1][2] = this;
    }
    
}
