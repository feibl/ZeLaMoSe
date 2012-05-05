/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.block;

import domain.Config;
import domain.block.wallkick.WallKickAbstract;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cyrill Lam <clam@hsr.ch>
 */
public abstract class BlockAbstract implements Cloneable {

    protected Color color;
    protected float glRed, glGreen, glBlue;

    
    protected BlockAbstract[][] grid = new BlockAbstract[Config.gridBlockWidth][Config.gridBlockHeight];
    protected int rotation, x, y;
    protected String printLetter;
    protected BlockRotationState blockRotationState;
    protected WallKickAbstract wallkick;
    private final int blockNumber;

    public int getHeight() {
        int height = 0;
        for(int i = 0; i < grid.length ; i++){
            for (int j = 0; j < grid.length; j++) {
                if (grid[j][i] != null){
                    height++;
                    break;
                }
            }
        }
        return height;
    }

    public int getBlockNumber() {
        return blockNumber;
    }

    public int getRotation() {
        return rotation;
    }

    public BlockRotationState getBlockRotationState(){
        return blockRotationState;
    }
    public int getWidth() {
        int width = 0;
        for(int i = 0; i < grid.length ; i++){
            for (int j = 0; j < grid.length; j++) {
                if (grid[i][j] != null){
                    width++;
                    break;
                }
            }
        }
        return width;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x  = x;
    }
    
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y  = y;
    }
     
    public BlockAbstract(Color c,String printLetter, WallKickAbstract wallkick,int blockNumber){
        color = c;
        glRed = convertRgbToGlColor(color.getRed());
        glBlue = convertRgbToGlColor(color.getBlue());
        glGreen = convertRgbToGlColor(color.getGreen());
        rotation = 0; 
        this.printLetter = printLetter;
        this.wallkick = wallkick;
        this.blockNumber = blockNumber;
        rotation0(grid);
    }
        
    protected void initializeGrid(BlockAbstract[][] grid) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                grid[i][j] = null;
            }
        }
    }
        
    public Color getColor(){
        return color;
    }
    

    public void rotateRight(int wallKickTestNumber) {
        rotation = (rotation + 90) % 360;
        handleRotation(true,wallKickTestNumber);
    }

    public void rotateLeft(int wallKickTestNumber) {
        if (rotation == 0) {
            rotation = 270;
        } else {
            rotation -= 90;
        }
        handleRotation(false,wallKickTestNumber);
    }
    
    protected abstract void rotation0 (BlockAbstract[][] grid);
    protected abstract void rotation90 (BlockAbstract[][] grid);
    protected abstract void rotation180 (BlockAbstract[][] grid);
    protected abstract void rotation270 (BlockAbstract[][] grid);
    
    public BlockAbstract[][] getGrid() {
        return grid;
    }
    
     protected void handleRotation(boolean rightRotation, int wallKickTestNumber) {
        initializeGrid(grid);
        switch (rotation) {
            case 0:
                blockRotationState = rightRotation ? BlockRotationState.r270r0 : BlockRotationState.r90r0;
                rotation0(grid);
                break;
            case 90:
                blockRotationState = rightRotation ? BlockRotationState.r0r90 : BlockRotationState.r180r90;
                rotation90(grid);
                break;
            case 180:
                blockRotationState = rightRotation ? BlockRotationState.r90r180 : BlockRotationState.r270r180;
                rotation180(grid);
                break;
            case 270:
                blockRotationState = rightRotation ? BlockRotationState.r180r270 : BlockRotationState.r0r270;
                rotation270(grid);
                break;
        }
        switch(wallKickTestNumber){
            case 2:
                wallkick.Test2(this);
                 break;
            case 3:
                wallkick.Test3(this);
                 break;
            case 4:
                wallkick.Test4(this);
                 break;
            case 5:
                wallkick.Test5(this);
                 break;
            default:
                wallkick.Test1(this);
                break;
        }
    
   }
       
   public String getPrintLetter(){
       return printLetter;
   }
   
   
   public Object clone(){
        try {
            return (BlockAbstract)super.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(BlockAbstract.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
   }
   public float getGlBlue() {
        return glBlue;
    }

    public float getGlGreen() {
        return glGreen;
    }

    public float getGlRed() {
        return glRed;
    }
   
    /**
     * Converts an RGB Color To openGL Color scala which has a Range from 0.0 to 1.0
     *
     * @param rgbColor
     * @return
     */
    private float convertRgbToGlColor(int rgbColor) {
        return (float) rgbColor / 255f;
    }
}
