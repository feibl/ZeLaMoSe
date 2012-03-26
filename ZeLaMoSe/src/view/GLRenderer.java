/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import domain.Action;
import domain.BlockQueue;
import domain.FakeGameEngine;
import domain.Stone;
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

    private int width, heigth, blocksize;
    private boolean[][] gamefield = new boolean[12][24];
    private FakeGameEngine engine;
    private Stone currentStone;
    private BlockQueue queue = new BlockQueue();
    private final int defaultX = 4, defaultY = 15;
    private int x = defaultX, y = defaultY;

    public GLRenderer(int width, int height, int blocksize) {
        this.width = width;
        this.heigth = height;
        this.blocksize = blocksize;
        currentStone = queue.getNextStone();
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        GLU glu = new GLU();

        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glLineWidth(1.0f);
        gl.glViewport(0, 0, width, heigth);
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(0, width, 0, heigth);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        drawGrid(gl);
        drawCurrentStone(gl);
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

    private void actionHandling(Action lastAction) {
        switch (lastAction.getType()) {
            case ROTATION_CLOCKWISE:
                rotateClocckwise();
                break;
            case ROTATION_COUNTERCLOCKWISE:
                rotateCounterclockwise();
                break;
            case SOFTDROP:
                softdrop();
                break;
        }
    }

    private void drawGrid(GL2 gl) {


        float red, green, blue;

        ////////////////////
        //drawing the grid
        red = 255f;
        green = 255f;
        blue = 255;

        gl.glColor3f(red, green, blue);

        gl.glBegin(GL.GL_LINES);

        //draw the vertical lines
        for (int x = 0; x <= width; x += blocksize) {
            gl.glVertex2d(x, 0);
            gl.glVertex2d(x, heigth);
        }

        //draw the horizontal lines
        for (int y = 0; y <= heigth; y += blocksize) {
            gl.glVertex2d(0, y);
            gl.glVertex2d(width, y);
        }

        gl.glEnd();
    }

    private void rotateClocckwise() {
        System.out.println("clockwise");
        currentStone.turnright();
    }

    private void rotateCounterclockwise() {
        System.out.println("counterclockwise");
        currentStone.turnleft();
    }

    private void softdrop() {
        y--;
        System.out.println("softdrop");
    }

    private void drawCurrentStone(GL2 gl) {
        Color stoneColor = currentStone.getColor();

        gl.glColor3f(stoneColor.getRed(), stoneColor.getGreen(), stoneColor.getBlue());

        gl.glBegin(GL2.GL_QUADS);

        boolean[][] grid = currentStone.getStoneGrid();
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
}
