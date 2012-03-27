/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.awt.Color;

/**
 *
 * @author Cyrill
 */
public abstract class Stone {

    protected Color color;
    protected boolean[][] stoneGrid = new boolean[4][4];
    protected int rotation;
     
    public Stone(Color c){
        color = c;
        rotation = 0; 
        rotation0(stoneGrid);
    }
        
    protected void initStoneGrid(boolean[][] grid) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                grid[i][j] = false;
            }
        }
    }
        
    public Color getColor(){
        return color;
    }
    

    public void turnright() {
        rotation = rotation + 90 % 360;
        calcGrid();
    }

    public void turnleft() {
        if (rotation == 0) {
            rotation = 270;
        } else {
            rotation -= 90;
        }
        calcGrid();
    }
    
    protected abstract void rotation0 (boolean[][] grid);
    protected abstract void rotation90 (boolean[][] grid);
    protected abstract void rotation180 (boolean[][] grid);
    protected abstract void rotation270 (boolean[][] grid);
    
    public boolean[][] getStoneGrid() {
        return stoneGrid;
    }
    
     protected void calcGrid() {
        initStoneGrid(stoneGrid);
        switch (rotation) {
            case 0:
                rotation0(stoneGrid);
                break;
            case 90:
                rotation90(stoneGrid);
                break;
            case 180:
                rotation180(stoneGrid);
                break;
            case 270:
                rotation270(stoneGrid);
                break;
        }
    
   }
     
    public void printstone() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(stoneGrid[j][i]);
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
   public void print() {

        System.out.println(rotation);
        for (int i = 0; i < 4; i++) {
            String lineOutput = "";
            for (int j = 0; j < 4; j++) {
                if (stoneGrid[j][i]) {
                    lineOutput += "[x]";
                } else {
                    lineOutput += "[ ]";
                }
            }
            System.out.println(lineOutput);
        }
        System.out.println("");
    }
   
   
    public static void main(String[] args) {

        Stone testStone = new StoneL();
        testStone.print();

        for (int i = 0; i < 4; i++) {
            testStone.turnleft();
            testStone.print();
        }

    }
}
