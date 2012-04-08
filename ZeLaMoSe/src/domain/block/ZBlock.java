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
public class ZBlock extends Block {

    public ZBlock() {
        super(new Color(255,0,0),"Z", new GeneralWallKick());
    }

    @Override
    protected void rotation0(Block[][] grid) {
        grid[0][0] = this;
        grid[1][0] = this;
        grid[1][1] = this;
        grid[2][1] = this;
    }

    @Override
    protected void rotation90(Block[][] grid) {
        grid[2][0] = this;
        grid[1][1] = this;
        grid[2][1] = this;
        grid[1][2] = this;
    }

    @Override
    protected void rotation180(Block[][] grid) {
        grid[0][1] = this;
        grid[1][1] = this;
        grid[1][2] = this;
        grid[2][2] = this;
    }

    @Override
    protected void rotation270(Block[][] grid) {
        grid[1][0] = this;
        grid[0][1] = this;
        grid[1][1] = this;
        grid[0][2] = this;
    }
    
}
