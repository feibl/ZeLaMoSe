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
public class LBlock extends BlockAbstract {

    public LBlock(int blockNumber,long seed) {
        super(new Color(255, 170, 0), "L", new GeneralWallKick(), blockNumber,seed);
    }

    @Override
    protected void rotation0() {
        grid[0][1] = this;
        grid[1][1] = this;
        grid[2][0] = this;
        grid[2][1] = this;
    }

    @Override
    protected void rotation90() {
        grid[1][0] = this;
        grid[1][1] = this;
        grid[1][2] = this;
        grid[2][2] = this;
    }

    @Override
    protected void rotation180() {
        grid[0][1] = this;
        grid[0][2] = this;
        grid[1][1] = this;
        grid[2][1] = this;
    }

    @Override
    protected void rotation270() {
        grid[0][0] = this;
        grid[1][0] = this;
        grid[1][1] = this;
        grid[1][2] = this;
    }
}
