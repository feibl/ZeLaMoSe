/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.block;

import domain.block.wallkick.IWallKick;
import java.awt.Color;

/**
 *
 * @author Patrick Zenhäusern <pzenhaeu@hsr.ch>
 */
public class IBlock extends BlockAbstract {
    
    
    public IBlock(int blockNumber,long seed){
        super(new Color(0,255,255),"I",new IWallKick(),blockNumber,seed);
    }

    @Override
    protected void rotation0(BlockAbstract[][] grid) {
        grid[0][1] = this;
        grid[1][1] = this;
        grid[2][1] = this;
        grid[3][1] = this;
    }

    @Override
    protected void rotation90(BlockAbstract[][] grid) {
        grid[2][0] = this;
        grid[2][1] = this;
        grid[2][2] = this;
        grid[2][3] = this;
    }

    @Override
    protected void rotation180(BlockAbstract[][] grid) {
        grid[0][2] = this;
        grid[1][2] = this;
        grid[2][2] = this;
        grid[3][2] = this;
    }

    @Override
    protected void rotation270(BlockAbstract[][] grid) {
        grid[1][0] = this;
        grid[1][1] = this;
        grid[1][2] = this;
        grid[1][3] = this;
    }
    
}
