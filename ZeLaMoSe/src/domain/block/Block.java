/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.block;

import java.awt.Color;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cyrill
 */
public abstract class Block implements Cloneable {

    protected Color color;
    protected boolean[][] grid = new boolean[4][4];
    protected int rotation, x, y;
    protected String printLetter;

    public int getHeight() {
        int height = 0;
        for(int i = 0; i < grid.length ; i++){
            for (int j = 0; j < grid.length; j++) {
                if (grid[j][i]){
                    height++;
                    break;
                }
            }
        }
        return height;
    }


    public int getWidth() {
        int width = 0;
        for(int i = 0; i < grid.length ; i++){
            for (int j = 0; j < grid.length; j++) {
                if (grid[i][j]){
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
     
    public Block(Color c,String printLetter){
        color = c;
        rotation = 0; 
        this.printLetter = printLetter;
        rotation0(grid);
    }
        
    protected void initializeGrid(boolean[][] grid) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                grid[i][j] = false;
            }
        }
    }
        
    public Color getColor(){
        return color;
    }
    

    public void rotateRight() {
        rotation = (rotation + 90) % 360;
        handleRotation();
    }

    public void rotateLeft() {
        if (rotation == 0) {
            rotation = 270;
        } else {
            rotation -= 90;
        }
        handleRotation();
    }
    
    protected abstract void rotation0 (boolean[][] grid);
    protected abstract void rotation90 (boolean[][] grid);
    protected abstract void rotation180 (boolean[][] grid);
    protected abstract void rotation270 (boolean[][] grid);
    
    public boolean[][] getGrid() {
        return grid;
    }
    
     protected void handleRotation() {
        initializeGrid(grid);
        switch (rotation) {
            case 0:
                rotation0(grid);
                break;
            case 90:
                rotation90(grid);
                break;
            case 180:
                rotation180(grid);
                break;
            case 270:
                rotation270(grid);
                break;
        }
    
   }
       
   public String getPrintLetter(){
       return printLetter;
   }
   
   public void print() {

        System.out.println(rotation);
        for (int i = 0; i < 4; i++) {
            String lineOutput = "";
            for (int j = 0; j < 4; j++) {
                if (grid[j][i]) {
                    lineOutput += "["+printLetter+"]";
                } else {
                    lineOutput += "[ ]";
                }
            }
            System.out.println(lineOutput);
        }
        System.out.println("");
    }
   
   public Object clone(){
        try {
            return (Block)super.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Block.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
   }
   
    public static void main(String[] args) {
        Random randomGenerator = new Random(System.currentTimeMillis());
//        Block testStone =  (BlockType.values()[randomGenerator.nextInt(BlockType.values().length)]).createBlock();
        Block testStone = new TBlock();
        for (int i = 0; i < 4; i++) {
            testStone.rotateRight();
            System.out.println("Height: " + testStone.getHeight());
            System.out.println("Width: " + testStone.getWidth());
            testStone.print();
        }

    }
}
