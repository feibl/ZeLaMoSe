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
public class OBlock extends Block {

    public OBlock() {
        super(new Color(255,255,0),"O");
    }

    @Override
    protected void rotation0(boolean[][] grid) {
                grid[1][0] = true;
        grid[2][0] = true;
        grid[1][1] = true;
        grid[2][1] = true;
    }

    @Override
    protected void rotation90(boolean[][] grid) {
       rotation0(grid);
    }

    @Override
    protected void rotation180(boolean[][] grid) {
       rotation0(grid);
    }

    @Override
    protected void rotation270(boolean[][] grid) {
       rotation0(grid);
    }
    
}
