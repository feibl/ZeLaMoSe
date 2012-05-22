package view;

import domain.SimulationStateAbstract;
import domain.block.BlockAbstract;
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
 * @author Cyrill Lam <clam@hsr.ch>
 */
class NextBlockRenderer implements GLEventListener, Observer {

    private BlockAbstract nextBlock;
    private int blockSize = 40;

    public NextBlockRenderer(SimulationStateAbstract gameEngine) {
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

        gl.glColor3f(0, 0, 0);

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
        nextBlock = ((SimulationStateAbstract) o).getNextBlock();
    }

    private void drawBlock(GL2 gl) {

        if (nextBlock != null) {
            gl.glColor3f(nextBlock.getGlRed(), nextBlock.getGlGreen(), nextBlock.getGlBlue());

            gl.glBegin(GL2.GL_QUADS);
            int x = nextBlock.getX();
            int y = nextBlock.getY();
            BlockAbstract[][] grid = nextBlock.getGrid();
            for (int a = 0; a < grid.length; a++) {
                for (int b = 0; b < grid.length; b++) {
                    if (grid[a][b] != null) {
                        gl.glVertex2i(blockSize * (x + a), blockSize * (y - b + 4));
                        gl.glVertex2i(blockSize * (x + a), blockSize * (y - b + 3));
                        gl.glVertex2i(blockSize * (x + 1 + a), blockSize * (y - b + 3));
                        gl.glVertex2i(blockSize * (x + 1 + a), blockSize * (y - b + 4));
                    }
                }
            }
            gl.glEnd();
        }
    }
}
