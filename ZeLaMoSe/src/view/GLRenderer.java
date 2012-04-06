/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import domain.Config;
import domain.GameEngine;
import domain.actions.*;
import domain.block.Block;
import domain.actions.RotateAction.Direction;
import domain.interfaces.SimulationStateInterface;
import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.*;
import javax.media.opengl.fixedfunc.GLMatrixFunc;
import javax.media.opengl.glu.GLU;

/**
 *
 * @author Cyrill
 */
class GLRenderer implements GLEventListener, Observer {

    private boolean debug = false;
    private volatile boolean isAnimating = false;
    private ConcurrentLinkedQueue<Action> actionQueue;
    private int viewPortWidth, viewPortHeight, blockSize;
    private SimulationStateInterface gameEngine;
    private Block currentBlock;
    private final int defaultX = 4, defaultY = 23;
    private volatile Color[][] grid;
    private Color backGroundColor = Color.BLACK;
    private Color garbageLineColor = new Color(139, 0, 0);

    public GLRenderer(int width, int height, int blocksize) {
        this.viewPortWidth = width;
        this.viewPortHeight = height;
        this.blockSize = blocksize;
        actionQueue = new ConcurrentLinkedQueue<Action>();
        initStackGrid();
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        GLU glu = new GLU();

        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glLineWidth(1.0f);
        gl.glViewport(0, 0, viewPortWidth, viewPortHeight);
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glLoadIdentity();

        if (debug) {
            glu.gluOrtho2D(-100, viewPortWidth + 100, -100, viewPortHeight + 100);
        } else {
            glu.gluOrtho2D(0, viewPortWidth, 0, viewPortHeight);
        }
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);


        drawBlockStack(gl);
        if (currentBlock != null) {
            drawCurrentBlock(gl);
        }
        drawGridLines(gl);

    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    void setEngine(SimulationStateInterface fakeGameEngine) {
        gameEngine = fakeGameEngine;
        gameEngine.addObserver(this);

    }

    @Override
    public void update(Observable o, Object o1) {
        processAction(gameEngine.getSimulationState());
    }

//   - rotation (direction): rotate current block in direction by 90
// * - move (direction, speed): move block in direction (left, right, down) with speed (number of grids) 
// * - rmline (number of lines, offset): remove a number of lines, first with offset from bottom
// * - newline (line definition): add new line to bottom. line according to supplied definition
// * - A new block enters the game
    private void processAction(Action action) {
        if (isAnimating) {
            actionQueue.add(action);
            if (debug) {
                System.out.println("GLREnderer: actiontype " + action.getType()+" stored in queue");
            }
        } else {
            
            handleAction(action);
        }

    }

    private void drawGridLines(GL2 gl) {


        float red, green, blue;
        ////////////////////
        //drawing the grid
        red = 0.2f;
        green = 0.2f;
        blue = 0.2f;

        gl.glColor3f(red, green, blue);

        gl.glBegin(GL.GL_LINES);

        //draw the vertical lines
        for (int x = 0; x <= viewPortWidth; x += blockSize) {
            gl.glVertex2d(x, 0);
            gl.glVertex2d(x, viewPortHeight);
        }

        //draw the horizontal lines
        for (int y = 0; y <= viewPortHeight; y += blockSize) {
            gl.glVertex2d(0, y);
            gl.glVertex2d(viewPortWidth, y);
        }

        gl.glEnd();
    }

    private void drawCurrentBlock(GL2 gl) {
        Color blockColor = currentBlock.getColor();

        gl.glColor3f(blockColor.getRed(), blockColor.getGreen(), blockColor.getBlue());

        gl.glBegin(GL2.GL_QUADS);
        int x = currentBlock.getX();
        int y = currentBlock.getY();
        boolean[][] grid = currentBlock.getGrid();
        for (int a = 0; a < grid.length; a++) {
            for (int b = 0; b < grid.length; b++) {
                if (grid[a][b]) {
                    gl.glVertex2i(blockSize * (x + a), blockSize * (y - b + 1));
                    gl.glVertex2i(blockSize * (x + a), blockSize * (y - b));
                    gl.glVertex2i(blockSize * (x + 1 + a), blockSize * (y - b));
                    gl.glVertex2i(blockSize * (x + 1 + a), blockSize * (y - b + 1));
                }
            }
        }
        gl.glEnd();
    }

    private void initStackGrid() {
        grid = new Color[Config.gridWidth][Config.gridHeight];
        for (int i = 0; i < Config.gridWidth; i++) {
            for (int j = 0; j < Config.gridHeight; j++) {
                grid[i][j] = backGroundColor;
            }
        }
    }

    private void drawBlockStack(GL2 gl) {

        for (int i = 0; i < Config.gridWidth; i++) {
            for (int j = 0; j < Config.gridHeight; j++) {

                Color colorField = grid[i][j];
                gl.glColor3f(colorField.getRed(), colorField.getGreen(), colorField.getBlue());

                gl.glBegin(GL2.GL_QUADS);

                gl.glVertex2i(blockSize * i, blockSize * (j + 1));
                gl.glVertex2i(blockSize * i, blockSize * (j));
                gl.glVertex2i(blockSize * (i + 1), blockSize * (j));
                gl.glVertex2i(blockSize * (i + 1), blockSize * (j + 1));

                gl.glEnd();
            }
        }


    }

    private void saveCurrentblockToGrid() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (currentBlock.getGrid()[i][j]) {
                    grid[currentBlock.getX() + i][currentBlock.getY() - j] = currentBlock.getColor();
                }
            }
        }
    }

    private void handleRotateAction(RotateAction action) {
        if (action.getDirection() == Direction.LEFT) {
            currentBlock.rotateLeft(Config.defaultWallKickTest);
        } else {
            currentBlock.rotateRight(Config.defaultWallKickTest);
        }
        currentBlock.setX(currentBlock.getX()+action.getXOffset());
        currentBlock.setY(currentBlock.getY()-action.getYOffset());
    }

    private void handleMoveAction(MoveAction action) {
        switch (action.getDirection()) {
            case DOWN:
                currentBlock.setY(currentBlock.getY() - action.getSpeed());
                break;
            case LEFT:
                currentBlock.setX(currentBlock.getX() - action.getSpeed());
                break;
            case RIGHT:
                currentBlock.setX(currentBlock.getX() + action.getSpeed());
                break;
        }
    }

    private void handleNewBlockAction(Block block) {
        if ((currentBlock != null)) {
            saveCurrentblockToGrid();
        }
        currentBlock = (Block) block.clone();
        currentBlock.setX(defaultX);
        currentBlock.setY(defaultY);

    }

    private void handleNewLineAction(boolean[][] line) {

        for (int i = 0; i < Config.gridWidth; i++) {
            for (int j = Config.gridHeight - 1 - line[0].length; j >= 0; j--) {
                grid[i][j + line[0].length] = grid[i][j];
            }
        }

        for (int i = 0; i < line.length; i++) {
            for (int j = 0; j < line[i].length; j++) {
                if (line[i][j]) {
                    grid[i][j] = garbageLineColor;
                } else {
                    grid[i][j] = backGroundColor;
                }
            }
        }
    }

    private Color[][] getGridCopy() {

        Color[][] copy = new Color[grid.length][grid[0].length];
        for (int i = 0; i < copy.length; i++) {
            copy[i] = Arrays.copyOf(grid[i], grid[i].length);
        }
        return copy;
    }

    private void handleRemoveLineAction(final RemoveLineAction rmlineAction) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                saveCurrentblockToGrid();
                currentBlock = null;

                Color[][] linesToRemoveMarkedGrid = getGridCopy();
                Color[][] originalGrid = getGridCopy();

                List<Integer> linesToRemove = rmlineAction.getLinesToRemove();

                for (Integer lineToRemove : linesToRemove) {

                    for (int x = 0; x < Config.gridWidth; x++) {
                        linesToRemoveMarkedGrid[x][23 - lineToRemove] = backGroundColor;
                    }
                }

                for (int i = 0; i < 4; i++) {
                    if (i % 2 == 0) {
                        grid = linesToRemoveMarkedGrid;
                    } else {
                        grid = originalGrid;
                    }
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GLRenderer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }


                for (Integer lineToRemove : linesToRemove) {
                    //remove the lineToRemove line
                    for (int x = 0; x < Config.gridWidth; x++) {
                        grid[x][23 - lineToRemove] = null;
                    }

                    //move everythign downward
                    for (int y = 23 - lineToRemove + 1; y <= 23; y++) {
                        for (int x = 0; x < Config.gridWidth; x++) {
                            grid[x][y - 1] = grid[x][y];
                        }
                    }
                }

                Action action;
                while ((action = actionQueue.poll()) != null) {
                    handleAction(action);
                }
                isAnimating = false;
            }
        }).start();
    }

    public void printGrid() {
        for (int i = Config.gridHeight - 1; i >= 0; i--) {
            String lineOutput = "";
            for (int j = 0; j < Config.gridWidth; j++) {
                if (grid[j][i] != backGroundColor) {
                    lineOutput += "[X]";
                } else {
                    lineOutput += "[ ]";
                }
            }
            System.out.println(lineOutput);
        }
        System.out.println("");
    }

    private void handleGameOverAction() {
        gameEngine.deleteObserver(this);
        currentBlock = null;
        new Thread(new Runnable() {

            @Override
            public void run() {
                boolean[][] filler = new boolean[Config.gridWidth][1];

                for (int j = 0; j < Config.gridWidth; j++) {
                    filler[j][0] = true;
                }
                for (int i = 0; i < Config.gridHeight; i++) {
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GLRenderer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    handleNewLineAction(filler);
                }
            }
        }).start();
    }

    private void handleAction(Action action) {
        switch (action.getType()) {
                case ROTATION:
                    handleRotateAction((RotateAction) action);
                    break;
                case MOVE:
                    handleMoveAction((MoveAction) action);
                    break;
                case NEWBLOCK:
                    handleNewBlockAction(((NewBlockAction) action).getBlocktype());
                    break;
                case NEWLINE:
                    handleNewLineAction(((NewLineAction) action).getLine());
                    break;
                case REMOVELINE:
                    isAnimating = true;
                    handleRemoveLineAction((RemoveLineAction) action);
                    break;
                case GAMEOVER:
                    handleGameOverAction();
                    break;
            }
            if (debug) {
                System.out.println("GLREnderer: actiontype " + action.getType()+" processed");
            }
    }
}
