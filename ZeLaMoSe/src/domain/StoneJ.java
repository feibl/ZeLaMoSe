/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.awt.Color;

/**
 *
 * @author Patrick Zenhäusern
 */
public class StoneJ extends Stone{

    public StoneJ() {
        super(new Color(0,255,0));
    }
    
    
    @Override
    protected void rotation0(boolean[][] grid) {
                grid[0][0] = true;
        grid[0][1] = true;
        grid[1][1] = true;
        grid[2][1] = true;
    }

    @Override
    protected void rotation90(boolean[][] grid) {
               grid[1][0] = true;
        grid[1][1] = true;
        grid[1][2] = true;
        grid[2][0] = true;
    }

    @Override
    protected void rotation180(boolean[][] grid) {
                grid[0][1] = true;
        grid[1][1] = true;
        grid[2][1] = true;
        grid[2][2] = true;
    }

    @Override
    protected void rotation270(boolean[][] grid) {
               grid[0][2] = true;
        grid[1][1] = true;
        grid[1][2] = true;
        grid[1][0] = true;
    }
    
}
