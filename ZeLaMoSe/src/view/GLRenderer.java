/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import domain.BlockQueue;
import domain.FakeGameEngine;
import domain.stone.Stone;
import domain.actions.Action;
import domain.actions.MoveAction;
import domain.actions.RotateAction;
import domain.actions.RotateAction.Direction;
import java.awt.Color;
import java.util.Observable;
import java.util.Observer;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.fixedfunc.GLMatrixFunc;
import javax.media.opengl.glu.GLU;

/**
 *
 * @author Cyrill
 */
class GLRenderer implements GLEventListener, Observer {

    private int viewportWidth, viewportHeight, blocksize;
    private final int gridWidth = 12, gridHeight=24;
    private FakeGameEngine engine;
    private Stone currentBlock;
    private BlockQueue queue = new BlockQueue();
    private final int defaultX = 4, defaultY = 15;
    private Color[][] stackGrid;

    public GLRenderer(int width, int height, int blocksize) {
        this.viewportWidth = width;
        this.viewportHeight = height;
        this.blocksize = blocksize;
        currentBlock = queue.nextBlock();
        initStackGrid();
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        GLU glu = new GLU();

        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glLineWidth(1.0f);
        gl.glViewport(0, 0, viewportWidth, viewportHeight);
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(0, viewportWidth, 0, viewportHeight);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        drawGrid(gl);
        drawStackGrid(gl);
        drawCurrentBlock(gl);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    void setEngine(FakeGameEngine fakeGameEngine) {
        engine = fakeGameEngine;
        engine.addObserver(this);

    }

    @Override
    public void update(Observable o, Object o1) {
        actionHandling(engine.getLastAction());
    }

    private void actionHandling(Action action) {
        switch (action.getType()) {
            case ROTATION:
                System.out.println("rotation");
                handleRotateAction(((RotateAction)action).getDirection());
                break;
            case MOVE:
                System.out.println("move");
                handleMoveAction(((MoveAction)action).getDirection());
                break;
            case HARDDROP:
                System.out.println("harddrop");
                HandleHarddropAction();
                break;
            case NEWBLOCK:
                System.out.println("newblock");
                HandleNewblockAction();
                break;
            case NEWLINE:
                System.out.println("newln");
                break;
            case RMLINE:
                System.out.println("rmln");
                break;
        }
    }

    private void drawGrid(GL2 gl) {


        float red, green, blue;
        ////////////////////
        //drawing the grid
        red = 0.2f;
        green = 0.2f;
        blue = 0.2f;

        gl.glColor3f(red, green, blue);

        gl.glBegin(GL.GL_LINES);

        //draw the vertical lines
        for (int x = 0; x <= viewportWidth; x += blocksize) {
            gl.glVertex2d(x, 0);
            gl.glVertex2d(x, viewportHeight);
        }

        //draw the horizontal lines
        for (int y = 0; y <= viewportHeight; y += blocksize) {
            gl.glVertex2d(0, y);
            gl.glVertex2d(viewportWidth, y);
        }

        gl.glEnd();
    }



    private void drawCurrentBlock(GL2 gl) {
        Color blockColor = currentBlock.getColor();

        gl.glColor3f(blockColor.getRed(), blockColor.getGreen(), blockColor.getBlue());

        gl.glBegin(GL2.GL_QUADS);
        int x = currentBlock.getX();
        int y = currentBlock.getY();
        boolean[][] grid = currentBlock.getStoneGrid();
        for (int a = 0; a < 4; a++) {
            for (int b = 0; b < 4; b++) {
                if (grid[a][b]) {
                    gl.glVertex2i(blocksize * (x + a), blocksize*(y-b));
                    gl.glVertex2i(blocksize * (x + a), blocksize*(y-b-1));
                    gl.glVertex2i(blocksize * (x + 1 + a) , blocksize*(y-b-1));
                    gl.glVertex2i(blocksize * (x + 1 + a), blocksize*(y-b));
                }
            }
        }
        gl.glEnd();
    }

    private void handleRotateAction(Direction direction) {
        if(direction == Direction.LEFT)
            currentBlock.turnleft();
        else
            currentBlock.turnright();
    }

    private void handleMoveAction(MoveAction.Direction direction) {
        switch(direction){
            case DOWN:
                currentBlock.setY(currentBlock.getY()-1);
                break;
            case LEFT:
                currentBlock.setX(currentBlock.getX()-1);
                break;
            case RIGHT:
                currentBlock.setX(currentBlock.getX()+1);
                break;
        }
    }

    private void HandleHarddropAction() {
        currentBlock.setY(2);
    }

    private void HandleNewblockAction() {
        currentBlock = queue.nextBlock();
        currentBlock.setX(defaultX);
        currentBlock.setY(defaultY);
    }

    private void initStackGrid() {
        stackGrid = new Color[gridWidth][gridHeight];
        for(int i = 0; i<gridWidth;i++){
            for(int j = 0; j < gridHeight; j++){
                stackGrid[i][j] = Color.BLACK;
            }
        }
    }

    private void drawStackGrid(GL2 gl) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
