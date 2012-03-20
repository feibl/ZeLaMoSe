/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jogl2;

import java.util.logging.Level;
import java.util.logging.Logger;
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
class Renderer implements GLEventListener {

    private int width, heigth, blocksize;
    private int posx = 4, posy = 24;
    private boolean drawGrid = true;
    private TestBlock block;
    final Renderer a;
    private BlockStack stack = new BlockStack();

    public Renderer(int width, int heigth, int blocksize) {
        this.width = width;
        this.heigth = heigth;
        this.blocksize = blocksize;
        this.block = new TestBlock();
        a = this;

        new Thread(new Runnable() {

            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        a.moveDown();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Renderer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();

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
    public void display(GLAutoDrawable drawable) {

        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        if (drawGrid) {
            drawGrid(gl);
        }

        drawStack(gl);
        drawBlock(gl);
        
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
//    throw new UnsupportedOperationException("Not supported yet.");
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

    private void drawBlock(GL2 gl) {
        float red, green, blue;


        red = 1f;
        green = 0.2f;
        blue = 0.2f;

        gl.glColor3f(red, green, blue);

        gl.glBegin(GL2.GL_QUADS);

        int[][] grid = block.stoneGrid;
        for (int a = 0; a < 4; a++) {
            for (int b = 0; b < 4; b++) {
                if (grid[a][b] == 1) {
                    
                    gl.glVertex2i(blocksize * (posx + a), blocksize * (posy - b));
                    gl.glVertex2i(blocksize * (posx + a), blocksize * (posy - 1 + b));
                    gl.glVertex2i(blocksize * (posx + (1 + a)), blocksize * (posy - 1 + b));
                    gl.glVertex2i(blocksize * (posx + (1 + a)), blocksize * (posy - b));

                }
            }

        }


        gl.glEnd();
    }

    public void moveRigth() {
        if ((posx + 1 + block.width) * blocksize <= width) {
            posx++;
        }
    }

    public void moveLeft() {
        if (posx - 1 != -1) {
            posx--;
        }
    }

    public void moveDown() {
        if (posy - block.height > 0) {
            posy--;
        } else {
            stack.add(block, posx, posy);
            block = new TestBlock();
            posx = 4;
            posy = 24;
        }
    }

    public void moveUp() {
        if (posy + block.height < 24) {
            posy++;
        }
    }

    void toggleGrid() {
        drawGrid = !drawGrid;
    }

    private void drawStack(GL2 gl) {
               float red, green, blue;


        red = 1f;
        green = 0.2f;
        blue = 0.2f;

        gl.glColor3f(red, green, blue);

        gl.glBegin(GL2.GL_QUADS);
        
        for (int i = 0; i < stack.getSize(); i++) {
            int[][] grid = stack.getBlock(i).stoneGrid;
            for (int a = 0; a < 4; a++) {
                for (int b = 0; b < 4; b++) {
                    if (grid[a][b] == 1) {
                        gl.glVertex2i(blocksize * (stack.getXpos(i) + a), blocksize * (stack.getypos(i) - b));
                        gl.glVertex2i(blocksize * (stack.getXpos(i) + a), blocksize * (stack.getypos(i) - 1 + b));
                        gl.glVertex2i(blocksize * (stack.getXpos(i) + (1 + a)), blocksize * (stack.getypos(i) - 1 + b));
                        gl.glVertex2i(blocksize * (stack.getXpos(i) + (1 + a)), blocksize * (stack.getypos(i) - b));
                    }
                }

            }
        }
        gl.glEnd();
    }

    void rotate() {
        block.rotate();
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }
}
