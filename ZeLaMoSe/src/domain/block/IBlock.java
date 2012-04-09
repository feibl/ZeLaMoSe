/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.block;

import domain.block.wallkick.IWallKick;
import java.awt.Color;

/**
 *
 * @author Patrick Zenhäusern
 */
public class IBlock extends Block {
    
    
    public IBlock(){
        super(new Color(100,100,100),"I",new IWallKick());
    }

    @Override
    protected void rotation0(Block[][] grid) {
        grid[0][1] = this;
        grid[1][1] = this;
        grid[2][1] = this;
        grid[3][1] = this;
    }

    @Override
    protected void rotation90(Block[][] grid) {
        grid[2][0] = this;
        grid[2][1] = this;
        grid[2][2] = this;
        grid[2][3] = this;
    }

    @Override
    protected void rotation180(Block[][] grid) {
        grid[0][2] = this;
        grid[1][2] = this;
        grid[2][2] = this;
        grid[3][2] = this;
    }

    @Override
    protected void rotation270(Block[][] grid) {
        grid[1][0] = this;
        grid[1][1] = this;
        grid[1][2] = this;
        grid[1][3] = this;
    }
    
}
