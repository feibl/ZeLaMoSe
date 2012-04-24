/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import domain.GameEngine;
import domain.block.BlockAbstract;
import domain.SimulationStateAbstract;
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
class NextBlockRenderer implements GLEventListener, Observer {
    
    
    private SimulationStateAbstract gameEngine;
    private BlockAbstract nextBlock;
    private int blockSize=40;

    public NextBlockRenderer(SimulationStateAbstract gameEngine) {
        this.gameEngine = gameEngine;
        gameEngine.addObserver(this);
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        GLU glu = new GLU();

        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glLineWidth(1.0f);
        gl.glViewport(0, 0, 150, 150);
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(-30, 190, -30, 190);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        drawBlock(gl);
        drawGridLines(gl);
        
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
        for (int x = 0; x <= 160; x += 40) {
            gl.glVertex2d(x, 0);
            gl.glVertex2d(x, 160);
        }

        //draw the horizontal lines
        for (int y = 0; y <= 160; y += 40) {
            gl.glVertex2d(0, y);
            gl.glVertex2d(160, y);
        }

        gl.glEnd();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    @Override
    public void update(Observable o, Object o1) {
        nextBlock = ((GameEngine)o).getNextBlock();
    }

    private void drawBlock(GL2 gl) {
        Color blockColor = nextBlock.getColor();


        gl.glColor3f(convertRgbToGlColor(blockColor.getRed()), convertRgbToGlColor(blockColor.getGreen()), convertRgbToGlColor(blockColor.getBlue()));

        gl.glBegin(GL2.GL_QUADS);
        int x = nextBlock.getX();
        int y = nextBlock.getY();
        BlockAbstract[][] grid = nextBlock.getGrid();
        for (int a = 0; a < grid.length; a++) {
            for (int b = 0; b < grid.length; b++) {
                if (grid[a][b] != null) {
                    gl.glVertex2i(blockSize * (x + a), blockSize * (y - b + 4));
                    gl.glVertex2i(blockSize * (x + a), blockSize * (y - b+3));
                    gl.glVertex2i(blockSize * (x + 1 + a), blockSize * (y - b+3));
                    gl.glVertex2i(blockSize * (x + 1 + a), blockSize * (y - b + 4));
                }
            }
        }
        gl.glEnd();
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
