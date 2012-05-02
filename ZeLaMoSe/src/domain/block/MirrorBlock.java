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
public class MirrorBlock extends BlockAbstract implements SpecialBlockInterface {

    public MirrorBlock(int blockNumber) {
        super(Color.white, "M", new NoWallKick(),blockNumber);
    }
    
    @Override
    protected void rotation0(BlockAbstract[][] grid) {
        grid[0][0] = this;
    
    }

    @Override
    protected void rotation90(BlockAbstract[][] grid) {
        rotation0(grid);
    }

    @Override
    protected void rotation180(BlockAbstract[][] grid) {
        rotation0(grid);
    }

    @Override
    protected void rotation270(BlockAbstract[][] grid) {
       rotation0(grid);
    }

    @Override
    public float getGlBlue() {
        glBlue=(glBlue+0.015f)%1;
        return super.getGlBlue();
    }

    @Override
    public float getGlGreen() {
        glGreen = (glGreen+0.01f)%1;
        return super.getGlGreen();
    }

    @Override
    public float getGlRed() {
        glRed = (glRed+0.016f)%1;
        return super.getGlRed();
    }
    
    
    
}
