package view;

import com.jogamp.opengl.util.awt.TextRenderer;
import domain.Config;
import domain.SimulationStateAbstract;
import domain.actions.*;
import domain.actions.RotateAction.Direction;
import domain.block.BlockAbstract;
import domain.block.GarbageBlock;
import domain.block.OBlock;
import java.awt.Font;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
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
    private final int defaultTimeToMirror = 15000;
    private final int defaultTimeToShadow = 15000;
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
    private volatile boolean isGameOver;
    private int rank = 0;
    private volatile boolean stopAnimationRequested;
    private Semaphore stopAnimationRequest;
    private Semaphore removeLineFinished;

    public GameFieldRenderer(int blocksize, SimulationStateAbstract gameEngine, boolean ownGameField) {
        this.blockSize = blocksize;
        this.ownGameField = ownGameField;
        viewPortWidth = Config.gridWidth * blocksize;
        viewPortHeight = (Config.gridHeight - 2) * blocksize;
        effectTextRenderer = new TextRenderer(new Font("Arial", Font.BOLD, 18), true, true);
        statusMsgsTextRenderer = new TextRenderer(new Font("Arial", Font.BOLD, 10), true, true);
        actionQueue = new ConcurrentLinkedQueue<Action>();

        fillStackGrid(null);

        if (gameEngine != null) {
            this.gameEngine = gameEngine;
            gameEngine.addObserver(this);
        }
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        GLU glu = new GLU();

        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
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
        switch ((SimulationStateAbstract.UpdateType) o1) {
            case LASTACTION:
                handleAction(gameEngine.getSimulationState());
                break;
            case RANKING:
                if (isGameOver) {
                    setRanking(((SimulationStateAbstract) o).getRank());
                }
                break;
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

    private void fillStackGrid(BlockAbstract block) {
        grid = new BlockAbstract[Config.gridWidth][Config.gridHeight];
        for (int i = 0; i < Config.gridWidth; i++) {
            for (int j = 0; j < Config.gridHeight; j++) {
                grid[i][j] = block;
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
            System.arraycopy(lines[x], 0, grid[x], 0, lines[x].length);
        }
    }

    @Override
    public String toString() {
        StringBuilder gridAsString = new StringBuilder();
        for (int i = Config.gridHeight - 1; i >= 0; i--) {
            for (int j = 0; j < Config.gridWidth; j++) {
                if (grid[j][i] != null) {
                    gridAsString.append("[").append(grid[j][i].getPrintLetter()).append("]");
                } else {
                    gridAsString.append("[ ]");
                }
            }
            gridAsString.append("\n");
        }
        gridAsString.append("\n");
        return gridAsString.toString();
    }

    private BlockAbstract[][] getGridCopy() {

        BlockAbstract[][] copy = new BlockAbstract[grid.length][grid[0].length];
        for (int i = 0; i < copy.length; i++) {
            copy[i] = Arrays.copyOf(grid[i], grid[i].length);
        }
        return copy;
    }

    private void handleRemoveLineAction(final RemoveLineAction rmlineAction) {
        saveCurrentblockToGrid();
        currentBlock = null;
        stopAnimationRequested = false;
        stopAnimationRequest = new Semaphore(0);
        removeLineFinished = new Semaphore(0);
        new Thread(new Runnable() {

            @Override
            public void run() {
                BlockAbstract[][] originalGrid = getGridCopy();
                BlockAbstract[][] linesToRemoveMarkedGrid = createMarkedGridCopy(rmlineAction.getLinesToRemove());

                for (int i = 0; i < 4 && !stopAnimationRequested; i++) {
                    if (i % 2 == 0) {
                        grid = linesToRemoveMarkedGrid;
                    } else {
                        grid = originalGrid;
                    }
                    try {
                        stopAnimationRequest.tryAcquire(200, TimeUnit.MILLISECONDS);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GameFieldRenderer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                removeLinesFromGrid(rmlineAction.getLinesToRemove());
                isAnimating = false;
                removeLineFinished.release();
            }
        }).start();
    }

    private BlockAbstract[][] createMarkedGridCopy(List<Integer> linesToRemove) {
        BlockAbstract[][] linesToRemoveMarkedGrid = getGridCopy();
        for (Integer lineToRemove : linesToRemove) {
            for (int x = 0; x < Config.gridWidth; x++) {
                linesToRemoveMarkedGrid[x][lineToRemove] = null;
            }
        }
        return linesToRemoveMarkedGrid;
    }

    private void removeLinesFromGrid(final List<Integer> linesToRemove) {
        for (Integer lineToRemove : linesToRemove) {
            for (int x = 0; x < Config.gridWidth; x++) {
                grid[x][lineToRemove] = null;
            }

            for (int y = lineToRemove + 1; y <= Config.gridHeight - 1; y++) {
                for (int x = 0; x < Config.gridWidth; x++) {
                    grid[x][y - 1] = grid[x][y];
                }
            }
        }
    }

    private void stopAnimation() {
        stopAnimationRequested = true;
        stopAnimationRequest.release();
        boolean acquired = false;
        while (!acquired) {
            try {
                removeLineFinished.acquire();
                acquired = true;
            } catch (InterruptedException ex) {
                Logger.getLogger(GameFieldRenderer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void handleGameOverAction() {
        currentBlock = null;

        new Thread(new Runnable() {

            @Override
            public void run() {
                BlockAbstract[][] filler = new BlockAbstract[Config.gridWidth][1];

                for (int j = 0; j < Config.gridWidth; j++) {
                    filler[j][0] = new GarbageBlock(Integer.MAX_VALUE, 0);
                }
                for (int i = 0; i < Config.gridHeight; i++) {
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GameFieldRenderer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    handleGarbageLineAction(filler);
                }
                isGameOver = true;
            }
        }).start();

    }

    private void handleAction(Action action) {
        if (isAnimating && stopAnimationRequired(action.getType())) {
            stopAnimation();
        }
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
                currentBlock.setY(currentBlock.getY() + ((GarbageLineAction) action).getYOffsetForCurrentBlock());
                break;
            case MIRROR:
                handleMirrorAction();
                break;
            case DARK:
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
                fillStackGrid(null);
                break;
        }
    }

    private boolean stopAnimationRequired(ActionType type) {
        switch (type) {
            case ROTATION:
            case MOVE:
            case MIRROR:
                return false;
            case NEWBLOCK:
                if (currentBlock != null) {
                    return true;
                }
                return false;
            case GARBAGELINE:
            case DARK:
            case REMOVELINE:
            case GAMEOVER:
            case CLEAR:
                return true;
        }
        return false;
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

    private void setRanking(int newRank) {
        BlockAbstract dummyBlock = new OBlock(0, 0);
        if (newRank != rank) {
            rank = newRank;
            fillStackGrid(new GarbageBlock(0, 0));
            switch (rank) {
                case 1:
                    grid[4][16] = dummyBlock;
                    grid[4][15] = dummyBlock;
                    grid[3][16] = dummyBlock;
                    grid[3][15] = dummyBlock;

                    grid[7][4] = dummyBlock;
                    grid[7][3] = dummyBlock;
                    grid[8][4] = dummyBlock;
                    grid[8][3] = dummyBlock;
                    grid[4][4] = dummyBlock;
                    grid[4][3] = dummyBlock;
                    grid[3][4] = dummyBlock;
                    grid[3][3] = dummyBlock;



                    grid[6][18] = dummyBlock;
                    grid[6][17] = dummyBlock;
                    grid[6][16] = dummyBlock;
                    grid[6][15] = dummyBlock;
                    grid[6][14] = dummyBlock;
                    grid[6][13] = dummyBlock;
                    grid[6][12] = dummyBlock;
                    grid[6][11] = dummyBlock;
                    grid[6][10] = dummyBlock;
                    grid[6][9] = dummyBlock;
                    grid[6][8] = dummyBlock;
                    grid[6][7] = dummyBlock;
                    grid[6][6] = dummyBlock;
                    grid[6][5] = dummyBlock;
                    grid[6][4] = dummyBlock;
                    grid[6][3] = dummyBlock;

                    grid[5][18] = dummyBlock;
                    grid[5][17] = dummyBlock;
                    grid[5][16] = dummyBlock;
                    grid[5][15] = dummyBlock;
                    grid[5][14] = dummyBlock;
                    grid[5][13] = dummyBlock;
                    grid[5][12] = dummyBlock;
                    grid[5][11] = dummyBlock;
                    grid[5][10] = dummyBlock;
                    grid[5][9] = dummyBlock;
                    grid[5][8] = dummyBlock;
                    grid[5][7] = dummyBlock;
                    grid[5][6] = dummyBlock;
                    grid[5][5] = dummyBlock;
                    grid[5][4] = dummyBlock;
                    grid[5][3] = dummyBlock;
                    break;
                case 2:
                    grid[8][18] = dummyBlock;
                    grid[7][18] = dummyBlock;
                    grid[6][18] = dummyBlock;
                    grid[5][18] = dummyBlock;
                    grid[4][18] = dummyBlock;
                    grid[3][18] = dummyBlock;
                    grid[8][17] = dummyBlock;
                    grid[7][17] = dummyBlock;
                    grid[6][17] = dummyBlock;
                    grid[5][17] = dummyBlock;
                    grid[4][17] = dummyBlock;
                    grid[3][17] = dummyBlock;

                    grid[2][16] = dummyBlock;
                    grid[1][16] = dummyBlock;
                    grid[2][15] = dummyBlock;
                    grid[1][15] = dummyBlock;

                    grid[10][16] = dummyBlock;
                    grid[9][16] = dummyBlock;
                    grid[10][15] = dummyBlock;
                    grid[9][15] = dummyBlock;

                    grid[10][14] = dummyBlock;
                    grid[9][14] = dummyBlock;
                    grid[10][13] = dummyBlock;
                    grid[9][13] = dummyBlock;

                    grid[8][12] = dummyBlock;
                    grid[7][12] = dummyBlock;
                    grid[8][11] = dummyBlock;
                    grid[7][11] = dummyBlock;

                    grid[6][10] = dummyBlock;
                    grid[5][10] = dummyBlock;
                    grid[6][9] = dummyBlock;
                    grid[5][9] = dummyBlock;

                    grid[4][8] = dummyBlock;
                    grid[3][8] = dummyBlock;
                    grid[4][7] = dummyBlock;
                    grid[3][7] = dummyBlock;

                    grid[2][6] = dummyBlock;
                    grid[1][6] = dummyBlock;
                    grid[2][5] = dummyBlock;
                    grid[1][5] = dummyBlock;

                    grid[10][4] = dummyBlock;
                    grid[9][4] = dummyBlock;
                    grid[8][4] = dummyBlock;
                    grid[7][4] = dummyBlock;
                    grid[6][4] = dummyBlock;
                    grid[5][4] = dummyBlock;
                    grid[4][4] = dummyBlock;
                    grid[3][4] = dummyBlock;
                    grid[2][4] = dummyBlock;
                    grid[1][4] = dummyBlock;
                    grid[10][3] = dummyBlock;
                    grid[9][3] = dummyBlock;
                    grid[8][3] = dummyBlock;
                    grid[7][3] = dummyBlock;
                    grid[6][3] = dummyBlock;
                    grid[5][3] = dummyBlock;
                    grid[4][3] = dummyBlock;
                    grid[3][3] = dummyBlock;
                    grid[2][3] = dummyBlock;
                    grid[1][3] = dummyBlock;
                    break;
                case 3:
                    grid[8][18] = dummyBlock;
                    grid[7][18] = dummyBlock;
                    grid[6][18] = dummyBlock;
                    grid[5][18] = dummyBlock;
                    grid[4][18] = dummyBlock;
                    grid[3][18] = dummyBlock;
                    grid[8][17] = dummyBlock;
                    grid[7][17] = dummyBlock;
                    grid[6][17] = dummyBlock;
                    grid[5][17] = dummyBlock;
                    grid[4][17] = dummyBlock;
                    grid[3][17] = dummyBlock;

                    grid[2][16] = dummyBlock;
                    grid[1][16] = dummyBlock;
                    grid[2][15] = dummyBlock;
                    grid[1][15] = dummyBlock;

                    grid[10][16] = dummyBlock;
                    grid[9][16] = dummyBlock;
                    grid[10][15] = dummyBlock;
                    grid[9][15] = dummyBlock;

                    grid[10][14] = dummyBlock;
                    grid[9][14] = dummyBlock;
                    grid[10][13] = dummyBlock;
                    grid[9][13] = dummyBlock;

                    grid[8][12] = dummyBlock;
                    grid[7][12] = dummyBlock;
                    grid[8][11] = dummyBlock;
                    grid[7][11] = dummyBlock;

                    grid[10][10] = dummyBlock;
                    grid[9][10] = dummyBlock;
                    grid[10][9] = dummyBlock;
                    grid[9][9] = dummyBlock;

                    grid[10][8] = dummyBlock;
                    grid[9][8] = dummyBlock;
                    grid[10][7] = dummyBlock;
                    grid[9][7] = dummyBlock;

                    grid[10][6] = dummyBlock;
                    grid[9][6] = dummyBlock;
                    grid[10][5] = dummyBlock;
                    grid[9][5] = dummyBlock;

                    grid[2][6] = dummyBlock;
                    grid[1][6] = dummyBlock;
                    grid[2][5] = dummyBlock;
                    grid[1][5] = dummyBlock;

                    grid[8][4] = dummyBlock;
                    grid[7][4] = dummyBlock;
                    grid[6][4] = dummyBlock;
                    grid[5][4] = dummyBlock;
                    grid[4][4] = dummyBlock;
                    grid[3][4] = dummyBlock;
                    grid[8][3] = dummyBlock;
                    grid[7][3] = dummyBlock;
                    grid[6][3] = dummyBlock;
                    grid[5][3] = dummyBlock;
                    grid[4][3] = dummyBlock;
                    grid[3][3] = dummyBlock;
                    break;
                case 4:
                    grid[6][16] = dummyBlock;
                    grid[5][16] = dummyBlock;
                    grid[6][15] = dummyBlock;
                    grid[5][15] = dummyBlock;

                    grid[4][14] = dummyBlock;
                    grid[3][14] = dummyBlock;
                    grid[4][13] = dummyBlock;
                    grid[3][13] = dummyBlock;

                    grid[2][12] = dummyBlock;
                    grid[1][12] = dummyBlock;
                    grid[2][11] = dummyBlock;
                    grid[1][11] = dummyBlock;

                    grid[10][10] = dummyBlock;
                    grid[9][10] = dummyBlock;
                    grid[8][10] = dummyBlock;
                    grid[7][10] = dummyBlock;
                    grid[6][10] = dummyBlock;
                    grid[5][10] = dummyBlock;
                    grid[4][10] = dummyBlock;
                    grid[3][10] = dummyBlock;
                    grid[2][10] = dummyBlock;
                    grid[1][10] = dummyBlock;
                    grid[10][9] = dummyBlock;
                    grid[9][9] = dummyBlock;
                    grid[8][9] = dummyBlock;
                    grid[7][9] = dummyBlock;
                    grid[6][9] = dummyBlock;
                    grid[5][9] = dummyBlock;
                    grid[4][9] = dummyBlock;
                    grid[3][9] = dummyBlock;
                    grid[2][9] = dummyBlock;
                    grid[1][9] = dummyBlock;

                    grid[8][18] = dummyBlock;
                    grid[7][18] = dummyBlock;
                    grid[8][17] = dummyBlock;
                    grid[7][17] = dummyBlock;
                    grid[8][16] = dummyBlock;
                    grid[7][16] = dummyBlock;
                    grid[8][15] = dummyBlock;
                    grid[7][15] = dummyBlock;
                    grid[8][14] = dummyBlock;
                    grid[7][14] = dummyBlock;
                    grid[8][13] = dummyBlock;
                    grid[7][13] = dummyBlock;
                    grid[8][12] = dummyBlock;
                    grid[7][12] = dummyBlock;
                    grid[8][11] = dummyBlock;
                    grid[7][11] = dummyBlock;

                    grid[8][10] = dummyBlock;
                    grid[7][10] = dummyBlock;
                    grid[8][9] = dummyBlock;
                    grid[7][9] = dummyBlock;
                    grid[8][8] = dummyBlock;
                    grid[7][8] = dummyBlock;
                    grid[8][7] = dummyBlock;
                    grid[7][7] = dummyBlock;
                    grid[8][6] = dummyBlock;
                    grid[7][6] = dummyBlock;
                    grid[8][5] = dummyBlock;
                    grid[7][5] = dummyBlock;
                    grid[8][4] = dummyBlock;
                    grid[7][4] = dummyBlock;
                    grid[8][3] = dummyBlock;
                    grid[7][3] = dummyBlock;
                    break;
            }
        }
    }
}
