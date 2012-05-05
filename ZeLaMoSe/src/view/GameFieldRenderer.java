/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.jogamp.opengl.util.awt.TextRenderer;
import domain.Config;
import domain.SimulationStateAbstract;
import domain.actions.*;
import domain.actions.RotateAction.Direction;
import domain.block.BlockAbstract;
import domain.block.GarbageBlock;
import java.awt.Font;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.*;
import javax.media.opengl.fixedfunc.GLMatrixFunc;
import javax.media.opengl.glu.GLU;

/**
 *
 * @author Cyrill Lam <clam@hsr.ch>
 */
class GameFieldRenderer implements GLEventListener, Observer {

    private long timeToMirror = 0;
    private long timeToShadow = 0;
    private volatile boolean isAnimating = false;
    private ConcurrentLinkedQueue<Action> actionQueue;
    private final int defaultTimeToMirror = 150000;
    private final int defaultTimeToShadow = 150000;
    private int viewPortWidth, viewPortHeight, blockSize;
    private SimulationStateAbstract gameEngine;
    private BlockAbstract currentBlock;
    private final int defaultX = 4, defaultY = 23;
    private volatile BlockAbstract[][] grid;
    private boolean activateMirrorEffect = false;
    private TextRenderer effectTextRenderer;
    private TextRenderer statusMsgsTextRenderer;
    private long mirrorStartTime;
    private long shadowStartTime;
    private boolean ownGameField;

    public GameFieldRenderer(int blocksize, SimulationStateAbstract gameEngine, boolean ownGameField) {
        this.blockSize = blocksize;
        this.ownGameField = ownGameField;
        viewPortWidth = Config.gridWidth * blocksize;
        viewPortHeight = (Config.gridHeight - 2) * blocksize;
        effectTextRenderer = new TextRenderer(new Font("Arial", Font.BOLD, 18), true, true);
        statusMsgsTextRenderer = new TextRenderer(new Font("Arial", Font.BOLD, 10), true, true);
        actionQueue = new ConcurrentLinkedQueue<Action>();

        initStackGrid();

        if (gameEngine != null) {
            this.gameEngine = gameEngine;
            gameEngine.addObserver(this);
        }
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

        glu.gluOrtho2D(0, viewPortWidth, 0, viewPortHeight);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    private void mirrorField(GL2 gl) {
        gl.glLoadIdentity();
        GLU glu = new GLU();
        glu.gluOrtho2D(viewPortWidth, 0, viewPortHeight, 0);
    }

    private void normalizeMirrorField(GL2 gl) {
        gl.glLoadIdentity();
        GLU glu = new GLU();
        glu.gluOrtho2D(0, viewPortWidth, 0, viewPortHeight);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        if (activateMirrorEffect) {
            mirrorField(gl);
            activateMirrorEffect = false;
        } else if (remainingMirrorTime() <= 0) {
            normalizeMirrorField(gl);
        }


        if (remainingShadowTime() > 0) {
            drawBlockStack(gl, true);
        } else {
            drawBlockStack(gl, false);
        }



        if (currentBlock != null) {
            drawCurrentBlock(gl);
        }
        drawGridLines(gl);


        if (ownGameField) {
            if (remainingMirrorTime() > 0) {
                String text = "Mirror Time Remaining: " + Double.toString(remainingMirrorTime() / 1000d);
                renderText(drawable, text, 30, 30, effectTextRenderer);
            }
            if (remainingShadowTime() > 0) {
                String text = "Time until the sun rises: " + Double.toString(remainingShadowTime() / 1000d);
                renderText(drawable, text, 30, 50, effectTextRenderer);
            }
        } else if (gameEngine == null) {
            renderText(drawable, "Player not connected", 10, 130, statusMsgsTextRenderer);

        }


    }

    private void renderText(GLAutoDrawable drawable, String text, int x, int y, TextRenderer renderer) throws GLException {
        renderer.beginRendering(drawable.getWidth(), drawable.getHeight());
        renderer.draw(text, x, y);
        renderer.endRendering();
    }

    private long remainingMirrorTime() {
        long remainingTime = timeToMirror - (System.currentTimeMillis() - mirrorStartTime);
        if (remainingTime < 0) {
            timeToMirror = 0;
        }
        return remainingTime;
    }

    private long remainingShadowTime() {
        long remainingTime = timeToShadow - (System.currentTimeMillis() - shadowStartTime);
        if (remainingTime < 0) {
            timeToShadow = 0;
        }
        return remainingTime;
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    @Override
    public void update(Observable o, Object o1) {
        //add actiontype1
        if ((SimulationStateAbstract.UpdateType) o1 == SimulationStateAbstract.UpdateType.LASTACTION) {
            processAction(gameEngine.getSimulationState());
        }

    }

    private void processAction(Action action) {
        if (isAnimating) {
            actionQueue.add(action);
        } else {
            handleAction(action);
        }

    }

    private void drawGridLines(GL2 gl) {


        gl.glColor3f(0, 0, 0);

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


        gl.glColor3f(currentBlock.getGlRed(), currentBlock.getGlGreen(), currentBlock.getGlBlue());

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
        grid = new BlockAbstract[Config.gridWidth][Config.gridHeight];
        for (int i = 0; i < Config.gridWidth; i++) {
            for (int j = 0; j < Config.gridHeight; j++) {
                grid[i][j] = null;
            }
        }
    }

    private void drawBlockStack(GL2 gl, boolean drawEverythingBlack) {

        for (int i = 0; i < Config.gridWidth; i++) {
            for (int j = 0; j < Config.gridHeight; j++) {

                BlockAbstract block = grid[i][j];

                if (block != null && !drawEverythingBlack) {
                    gl.glColor3f(block.getGlRed(), block.getGlGreen(), block.getGlBlue());
                } else {
                    gl.glColor3f(0, 0, 0);
                }

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
                    grid[currentBlock.getX() + i][currentBlock.getY() - j] = currentBlock;
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
        currentBlock.setX(currentBlock.getX() + action.getXOffset());
        currentBlock.setY(currentBlock.getY() + action.getYOffset());
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

    private void handleNewBlockAction(BlockAbstract block) {
        if ((currentBlock != null)) {
            saveCurrentblockToGrid();
        }
        currentBlock = (BlockAbstract) block.clone();
        currentBlock.setX(defaultX);
        currentBlock.setY(defaultY);

    }

    private void handleGarbageLineAction(BlockAbstract[][] lines) {

        for (int x = 0; x < Config.gridWidth; x++) {
            for (int y = Config.gridHeight - 1 - lines[0].length; y >= 0; y--) {
                grid[x][y + lines[0].length] = grid[x][y];
            }
        }

        for (int x = 0; x < lines.length; x++) {
            for (int y = 0; y < lines[x].length; y++) {
                if (lines[x][y] != null) {
                    grid[x][y] = lines[x][y];
                } else {
                    grid[x][y] = null;
                }
            }
        }
    }

    private BlockAbstract[][] getGridCopy() {

        BlockAbstract[][] copy = new BlockAbstract[grid.length][grid[0].length];
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

                BlockAbstract[][] linesToRemoveMarkedGrid = getGridCopy();
                BlockAbstract[][] originalGrid = getGridCopy();

                List<Integer> linesToRemove = rmlineAction.getLinesToRemove();
                for (Integer lineToRemove : linesToRemove) {

                    for (int x = 0; x < Config.gridWidth; x++) {
                        linesToRemoveMarkedGrid[x][lineToRemove] = null;
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
                        Logger.getLogger(GameFieldRenderer.class.getName()).log(Level.SEVERE, null, ex);
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
                if (grid[j][i] != null) {
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
                BlockAbstract[][] filler = new BlockAbstract[Config.gridWidth][1];

                for (int j = 0; j < Config.gridWidth; j++) {
                    filler[j][0] = new GarbageBlock(Integer.MAX_VALUE);
                }
                for (int i = 0; i < Config.gridHeight; i++) {
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GameFieldRenderer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    handleGarbageLineAction(filler);
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
            case GARBAGELINE:
                handleGarbageLineAction(((GarbageLineAction) action).getLines());
                break;
            case MIRROR:
                handleMirrorAction();
                break;
            case SHADOW:
                handleShadowAction();
                break;
            case REMOVELINE:
                isAnimating = true;
                handleRemoveLineAction((RemoveLineAction) action);
                break;
            case GAMEOVER:
                handleGameOverAction();
                break;
            case CLEAR:
                initStackGrid();
                break;
        }
    }

    private void handleMirrorAction() {
        activateMirrorEffect = true;
        if (timeToMirror == 0) {
            timeToMirror = defaultTimeToMirror;
            mirrorStartTime = System.currentTimeMillis();
        } else {
            timeToMirror += defaultTimeToMirror;
        }
    }

    private void handleShadowAction() {
        if (timeToShadow == 0) {
            timeToShadow = defaultTimeToShadow;
            shadowStartTime = System.currentTimeMillis();
        } else {
            timeToShadow += defaultTimeToShadow;
        }
    }
}
