/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.block;

import java.awt.Color;

/**
 *
 * @author Patrick Zenhäusern
 */
public class TBlock extends Block {

    public TBlock() {
        super(new Color(160,32,240),"T");
    }

    @Override
    protected void rotation0(boolean[][] grid) {
        grid[1][0] = true;
        grid[0][1] = true;
        grid[1][1] = true;
        grid[2][1] = true;
    }

    @Override
    protected void rotation90(boolean[][] grid) {
        grid[1][0] = true;
        grid[1][1] = true;
        grid[2][1] = true;
        grid[1][2] = true;
    }

    @Override
    protected void rotation180(boolean[][] grid) {
        grid[0][1] = true;
        grid[1][1] = true;
        grid[2][1] = true;
        grid[1][2] = true;
    }

    @Override
    protected void rotation270(boolean[][] grid) {
        grid[1][0] = true;
        grid[0][1] = true;
        grid[1][1] = true;
        grid[1][2] = true;
    }
    
}
