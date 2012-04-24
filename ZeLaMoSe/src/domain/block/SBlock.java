/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.block;

import domain.block.wallkick.GeneralWallKick;
import java.awt.Color;

/**
 *
 * @author Patrick Zenhäusern <pzenhaeu@hsr.ch>
 */
public class SBlock extends BlockAbstract {

    public SBlock() {
        super(new Color(0,139,0),"S", new GeneralWallKick());
    }

    @Override
    protected void rotation0(BlockAbstract[][] grid) {
        grid[1][0] = this;
        grid[2][0] = this;
        grid[0][1] = this;
        grid[1][1] = this;
    }

    @Override
    protected void rotation90(BlockAbstract[][] grid) {
        grid[1][0] = this;
        grid[1][1] = this;
        grid[2][1] = this;
        grid[2][2] = this;
    }

    @Override
    protected void rotation180(BlockAbstract[][] grid) {
        grid[1][1] = this;
        grid[2][1] = this;
        grid[0][2] = this;
        grid[1][2] = this;
    }

    @Override
    protected void rotation270(BlockAbstract[][] grid) {
        grid[0][0] = this;
        grid[0][1] = this;
        grid[1][1] = this;
        grid[1][2] = this;
    }
    
}
