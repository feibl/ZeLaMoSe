/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.block;

import domain.block.wallkick.NoWallKick;
import java.awt.Color;

/**
 *
 * @author Cyrill Lam <clam@hsr.ch>
 */
public class DarkBlock extends BlockAbstract implements SpecialBlockInterface {

    public DarkBlock(int blockNumber) {
        super(new Color(20,20,20), "S", new NoWallKick(),blockNumber);
    }
    
    @Override
    protected void rotation0(BlockAbstract[][] grid) {
        grid[1][0] = this;
        grid[2][0] = this;
        grid[1][1] = this;
        grid[3][1] = this;
        grid[1][2] = this;
        grid[3][2] = this;
        grid[1][3] = this;
        grid[2][3] = this;
    }

    @Override
    protected void rotation90(BlockAbstract[][] grid) {
        grid[1][0] = this;
        grid[2][0] = this;
        grid[3][0] = this;
        grid[1][1] = this;
        grid[1][2] = this;
        grid[3][1] = this;
        grid[1][2] = this;
        grid[2][2] = this;
        grid[3][2] = this;
        grid[1][3] = this;
        grid[3][3] = this;
    }

    @Override
    protected void rotation180(BlockAbstract[][] grid) {
        grid[1][0] = this;
        grid[2][0] = this;
        grid[3][0] = this;
        grid[1][1] = this;
        grid[3][1] = this;
        grid[1][2] = this;
        grid[2][2] = this;
        grid[1][3] = this;
        grid[3][3] = this;
    }

    @Override
    protected void rotation270(BlockAbstract[][] grid) {
        grid[1][0] = this;
        grid[3][0] = this;
        grid[1][1] = this;
        grid[2][1] = this;
        grid[1][2] = this;
        grid[2][2] = this;
        grid[1][3] = this;
        grid[3][3] = this;
    }
    
    
    
    
}
