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
public class SBlock extends Block {

    public SBlock() {
        super(new Color(0,255,0),"S", new GeneralWallKick());
    }

    @Override
    protected void rotation0(boolean[][] grid) {
        grid[1][0] = true;
        grid[2][0] = true;
        grid[0][1] = true;
        grid[1][1] = true;
    }

    @Override
    protected void rotation90(boolean[][] grid) {
        grid[1][0] = true;
        grid[1][1] = true;
        grid[2][1] = true;
        grid[2][2] = true;
    }

    @Override
    protected void rotation180(boolean[][] grid) {
        grid[1][1] = true;
        grid[2][1] = true;
        grid[0][2] = true;
        grid[1][2] = true;
    }

    @Override
    protected void rotation270(boolean[][] grid) {
        grid[0][0] = true;
        grid[0][1] = true;
        grid[1][1] = true;
        grid[1][2] = true;
    }
    
}
