/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jogl2;

/**
 *
 * @author Cyrill
 */
class TestBlock {

    public int[][] stoneGrid = new int[4][4];
    public int width;
    public int height;

    public TestBlock() {
        iBlock(stoneGrid);
    }

    private void initStoneGrid(int[][] grid) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                grid[i][j] = 0;
            }
        }
    }

    private void iBlock(int[][] grid) {
        initStoneGrid(grid);
        grid[0][1] = 1;
        grid[1][1] = 1;
        grid[2][1] = 1;
        grid[3][1] = 1;
        width = 4;
        height = 1;
    }

    private void iBlock90(int[][] grid) {
        initStoneGrid(grid);
        grid[1][0] = 1;
        grid[1][1] = 1;
        grid[1][2] = 1;
        grid[1][3] = 1;
        width = 1;
        height = 4;
    }

    void rotate() {
        if (width == 4) {
            iBlock90(stoneGrid);
        } else {
            iBlock(stoneGrid);
        }
    }
}
