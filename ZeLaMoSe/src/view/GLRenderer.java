/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import domain.Config;
import domain.SimulationStateAbstract;
import domain.actions.*;
import domain.actions.RotateAction.Direction;
import domain.block.BlockAbstract;
import domain.block.GarbageBlock;
import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.fixedfunc.GLMatrixFunc;
import javax.media.opengl.glu.GLU;
import view.music.MusicEngineAbstract;
import view.music.MusicFile;
import view.music.OffMusicEngine;
import view.music.OnMusicEngine;

/**
 *
 * @author Cyrill Lam <clam@hsr.ch>
 */
class GLRenderer implements GLEventListener, Observer {

    private boolean debug = false;
    private volatile boolean isAnimating = false;
    private ConcurrentLinkedQueue<Action> actionQueue;
    private int viewPortWidth, viewPortHeight, blockSize;
    private SimulationStateAbstract gameEngine;
    private BlockAbstract currentBlock;
    private final int defaultX = 4, defaultY = 23;
    private volatile Color[][] grid;
    private Color backGroundColor = Color.BLACK;
    private MusicEngineAbstract backGroundMusic, rotateSound, moveSound, dropSound, lineRemovedSound, fourLineRemovedSound, gameOverSound;

    public GLRenderer(int blocksize, boolean useSound, SimulationStateAbstract gameEngine) {
        this.viewPortWidth = Config.gridWidth * blocksize;
        this.viewPortHeight = (Config.gridHeight - 2) * blocksize;
        this.blockSize = blocksize;
        actionQueue = new ConcurrentLinkedQueue<Action>();
        initStackGrid();
        initSoundEngine(useSound);

        if (gameEngine != null) {
            this.gameEngine = gameEngine;
            gameEngine.addObserver(this);
        }
    }

    public void initSoundEngine(boolean useSound) {
        if (useSound) {
            backGroundMusic = new OnMusicEngine(MusicFile.gameBackgroundMusic);
            rotateSound = new OnMusicEngine(MusicFile.rotateSound);
            moveSound = new OnMusicEngine(MusicFile.moveSound);
            dropSound = new OnMusicEngine(MusicFile.dropSound);
            lineRemovedSound = new OnMusicEngine(MusicFile.lineRemovedSound);
            fourLineRemovedSound = new OnMusicEngine(MusicFile.fourLineRemovedSound);
            gameOverSound = new OnMusicEngine(MusicFile.gameOverSound);
        } else {
            if (backGroundMusic != null) {
                backGroundMusic.stopMusic();
            }
            backGroundMusic = new OffMusicEngine();
            rotateSound = new OffMusicEngine();
            moveSound = new OffMusicEngine();
            dropSound = new OffMusicEngine();
            lineRemovedSound = new OffMusicEngine();
            fourLineRemovedSound = new OffMusicEngine();
            gameOverSound = new OffMusicEngine();
        }
        backGroundMusic.playMusic(true, 0.75f);
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

    @Override
    public void update(Observable o, Object o1) {
        processAction(gameEngine.getSimulationState());
    }

    private void processAction(Action action) {
        if (isAnimating) {
            actionQueue.add(action);
            if (debug) {
                System.out.println("GLREnderer: actiontype " + action.getType() + " stored in queue");
            }
        } else {

            handleAction(action);
        }

    }

    private void drawGridLines(GL2 gl) {


        float red, green, blue;
        ////////////////////
        //drawing the grid
        red = 0.0f;
        green = 0.0f;
        blue = 0.0f;

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


        gl.glColor3f(convertRgbToGlColor(blockColor.getRed()), convertRgbToGlColor(blockColor.getGreen()), convertRgbToGlColor(blockColor.getBlue()));

        gl.glBegin(GL2.GL_QUADS);
        int x = currentBlock.getX();
        int y = currentBlock.getY();
        BlockAbstract[][] grid = currentBlock.getGrid();
        for (int a = 0; a < grid.length; a++) {
            for (int b = 0; b < grid.length; b++) {
                if (grid[a][b] != null) {
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
                gl.glColor3f(convertRgbToGlColor(colorField.getRed()), convertRgbToGlColor(colorField.getGreen()), convertRgbToGlColor(colorField.getBlue()));

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
                if (currentBlock.getGrid()[i][j] != null) {
                    grid[currentBlock.getX() + i][currentBlock.getY() - j] = currentBlock.getColor();
                }
            }
        }
    }

    private void handleRotateAction(RotateAction action) {
        rotateSound.playMusic(false);

        if (action.getDirection() == Direction.LEFT) {
            currentBlock.rotateLeft(Config.defaultWallKickTest);
        } else {
            currentBlock.rotateRight(Config.defaultWallKickTest);
        }
        currentBlock.setX(currentBlock.getX() + action.getXOffset());
        currentBlock.setY(currentBlock.getY() + action.getYOffset());
    }

    private void handleMoveAction(MoveAction action) {

        switch (action.getDirection()) {
            case DOWN:
                currentBlock.setY(currentBlock.getY() - action.getSpeed());
                break;
            case LEFT:
                moveSound.playMusic(false);
                currentBlock.setX(currentBlock.getX() - action.getSpeed());
                break;
            case RIGHT:
                moveSound.playMusic(false);
                currentBlock.setX(currentBlock.getX() + action.getSpeed());
                break;
        }
    }

    private void handleNewBlockAction(BlockAbstract block) {
        if ((currentBlock != null)) {
            dropSound.playMusic(false);
            saveCurrentblockToGrid();
        }
        currentBlock = (BlockAbstract) block.clone();
        currentBlock.setX(defaultX);
        currentBlock.setY(defaultY);

    }

    private void handleNewLineAction(BlockAbstract[][] lines) {

        for (int x = 0; x < Config.gridWidth; x++) {
            for (int y = Config.gridHeight - 1 - lines[0].length; y >= 0; y--) {
                grid[x][y + lines[0].length] = grid[x][y];
            }
        }

        for (int x = 0; x < lines.length; x++) {
            for (int y = 0; y < lines[x].length; y++) {
                if (lines[x][y] != null) {
                    grid[x][y] = lines[x][y].getColor();
                } else {
                    grid[x][y] = backGroundColor;
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
                if (linesToRemove.size() == 4) {
                    fourLineRemovedSound.playMusic(false);
                } else {
                    lineRemovedSound.playMusic(false);
                }
                for (Integer lineToRemove : linesToRemove) {

                    for (int x = 0; x < Config.gridWidth; x++) {
                        linesToRemoveMarkedGrid[x][lineToRemove] = backGroundColor;
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
                        grid[x][lineToRemove] = null;
                    }

                    //move everythign downward
                    for (int y = lineToRemove + 1; y <= Config.gridHeight - 1; y++) {
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
                backGroundMusic.stopMusic();
                gameOverSound.playMusic(false);
                BlockAbstract[][] filler = new BlockAbstract[Config.gridWidth][1];

                for (int j = 0; j < Config.gridWidth; j++) {
                    filler[j][0] = new GarbageBlock();
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
                handleNewLineAction(((GarbageLineAction) action).getLines());
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
            System.out.println("GLREnderer: actiontype " + action.getType() + " processed");
        }
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
