package domain;

import java.util.Arrays;

public class Stone {

    // Array in dem die Steine liegen
    private boolean[][] stoneGrid = new boolean[4][4];
    private int rotation; // Winkel um den gedreht ist
    private int width; // Aktuelle breite des Steins
    private int height; // Aktuelle H�he des Steins
    private StoneType type;

    //	 Array auf 0 setzen
    private void initStoneGrid(boolean[][] grid) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                grid[i][j] = false;
            }
        }
    }

    /**
     * Default position of an I-Block
     *
     * @param grid
     */
    private void I0(boolean[][] grid) {
        initStoneGrid(grid);
        grid[0][2] = true;
        grid[1][2] = true;
        grid[2][2] = true;
        grid[3][2] = true;
    }

    /**
     * Position of an I-Block clockwise turned by 90 deg
     *
     * @param grid
     */
    private void I90(boolean[][] grid) {
        initStoneGrid(grid);
        grid[2][0] = true;
        grid[2][1] = true;
        grid[2][2] = true;
        grid[2][3] = true;
    }

    /**
     * Position of an I-Block clockwise turned by 180 deg
     *
     * @param grid
     */
    private void I180(boolean[][] grid) {
        initStoneGrid(grid);
        grid[0][1] = true;
        grid[1][1] = true;
        grid[2][1] = true;
        grid[3][1] = true;
    }

    /**
     * Position of an I-Block clockwise turned by 180 deg
     *
     * @param grid
     */
    private void I270(boolean[][] grid) {
        initStoneGrid(grid);
        grid[1][0] = true;
        grid[1][1] = true;
        grid[1][2] = true;
        grid[1][3] = true;
    }

    /**
     * Default position of an J-Block
     *
     * @param grid
     */
    private void J0(boolean[][] grid) {
        initStoneGrid(grid);
        grid[0][3] = true;
        grid[0][2] = true;
        grid[1][2] = true;
        grid[2][2] = true;
    }

    /**
     * Position of an j-Block clockwise turned by 90 deg
     *
     * @param grid
     */
    private void J90(boolean[][] grid) {
        initStoneGrid(grid);
        grid[1][3] = true;
        grid[1][2] = true;
        grid[1][1] = true;
        grid[2][3] = true;
    }

    /**
     * Position of an j-Block clockwise turned by 180 deg
     *
     * @param grid
     */
    private void J180(boolean[][] grid) {
        initStoneGrid(grid);
        grid[0][2] = true;
        grid[1][2] = true;
        grid[2][2] = true;
        grid[2][1] = true;
    }

    /**
     * Position of an j-Block clockwise turned by 270 deg
     *
     * @param grid
     */
    private void J270(boolean[][] grid) {
        initStoneGrid(grid);
        grid[0][1] = true;
        grid[1][3] = true;
        grid[1][2] = true;
        grid[1][1] = true;
    }

    /**
     * Default position of an L-Block
     *
     * @param grid
     */
    private void L0(boolean[][] grid) {
        initStoneGrid(grid);
        grid[0][2] = true;
        grid[1][2] = true;
        grid[2][2] = true;
        grid[2][3] = true;
    }

    /**
     * Position of an L-Block clockwise turned by 90 deg
     *
     * @param grid
     */
    private void L90(boolean[][] grid) {
        initStoneGrid(grid);
        grid[1][3] = true;
        grid[1][2] = true;
        grid[1][1] = true;
        grid[2][1] = true;
    }

    /**
     * Position of an L-Block clockwise turned by 180 deg
     *
     * @param grid
     */
    private void L180(boolean[][] grid) {
        initStoneGrid(grid);
        grid[0][1] = true;
        grid[0][2] = true;
        grid[1][2] = true;
        grid[2][2] = true;
    }

    /**
     * Position of an L-Block clockwise turned by 270 deg
     *
     * @param grid
     */
    private void L270(boolean[][] grid) {
        initStoneGrid(grid);
        grid[0][3] = true;
        grid[1][3] = true;
        grid[1][2] = true;
        grid[1][1] = true;
    }

    /**
     * Konstruktor
     */
    public Stone(StoneType type) {
        rotation = 0; // Am Anfang keine Drehung
        this.type = type;
        switch (type) {
            case I:
                I0(stoneGrid);
                break;
            case J:
                J0(stoneGrid);
                break;
            case L:
                L0(stoneGrid);
                break;
        }
    }

    public boolean[][] getStoneGrid() {
        return stoneGrid;
    }

    private void calcGrid() {
        switch (type) {

            case I:
                switch (rotation) {
                    case 0:
                        I0(stoneGrid);
                        break;
                    case 90:
                        I90(stoneGrid);
                        break;
                    case 180:
                        I180(stoneGrid);
                        break;
                    case 270:
                        I270:
                        break;

                }
                break;
            case J:
                switch (rotation) {
                    case 0:
                        J0(stoneGrid);
                        break;
                    case 90:
                        J90(stoneGrid);
                        break;
                    case 180:
                        J180(stoneGrid);
                        break;
                    case 270:
                        J270:
                        break;

                }
                break;
            case L:
                switch (rotation) {
                    case 0:
                        L0(stoneGrid);
                        break;
                    case 90:
                        L90(stoneGrid);
                        break;
                    case 180:
                        L180(stoneGrid);
                        break;
                    case 270:
                        L270:
                        break;

                }
                break;

        }
    }

    public void turnright() {
        rotation = (rotation+ 90) % 360;
        calcGrid();
    }

    public void turnleft() {
        if (rotation == 0) {
            rotation = 270;
        } else {
            rotation = rotation - 90;
        }
        calcGrid();
    }

    /**
     * Aktuellen Stein auf der Konsole ausgeben
     */
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

    /**
     * Main zum testen
     */
    public static void main(String[] args) {


        Stone testStone = new Stone(StoneType.L);
        
        testStone.turnright();
        testStone.print();
        
        testStone.turnright();
        testStone.print();
        
        testStone.turnright();
        testStone.print();
        
        testStone.turnright();
        testStone.print();
        
        
        
    }
    
    public  void print(){
        System.out.println(rotation);
        System.out.println(Arrays.toString(getStoneGrid()[0]));
        System.out.println(Arrays.toString(getStoneGrid()[1]));
        System.out.println(Arrays.toString(getStoneGrid()[2]));
        System.out.println(Arrays.toString(getStoneGrid()[3]));
        System.out.println("");
    }
}