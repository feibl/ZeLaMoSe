/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.block;

import domain.block.wallkick.NoWallKick;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Cyrill Lam <clam@hsr.ch>
 */
public class MirrorBlock extends BlockAbstract implements SpecialBlockInterface {

    public MirrorBlock(int blockNumber,long seed) {
        super(Color.white, "M", new NoWallKick(),blockNumber,seed);
    }
    
    @Override
    protected void rotation0() {
       // grid[0][0] = this;
        grid[posList.get(0)][posList.get(1)] = this;
        grid[posList.get(2)][posList.get(3)] = this;
        grid[posList.get(4)][posList.get(5)] = this;
        grid[posList.get(6)][posList.get(7)] = this;
    
    }

    @Override
    protected void rotation90() {
        grid[posList.get(8)][posList.get(9)] = this;
        grid[posList.get(10)][posList.get(11)] = this;
        grid[posList.get(12)][posList.get(13)] = this;
        grid[posList.get(14)][posList.get(15)] = this;
    }

    @Override
    protected void rotation180() {
        grid[posList.get(16)][posList.get(17)] = this;
        grid[posList.get(18)][posList.get(10)] = this;
        grid[posList.get(20)][posList.get(21)] = this;
        grid[posList.get(22)][posList.get(23)] = this;
    }

    @Override
    protected void rotation270() {
        grid[posList.get(24)][posList.get(25)] = this;
        grid[posList.get(26)][posList.get(27)] = this;
        grid[posList.get(28)][posList.get(29)] = this;
        grid[posList.get(30)][posList.get(31)] = this;
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
