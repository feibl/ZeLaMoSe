/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jogl2;

import com.jogamp.opengl.util.FPSAnimator;
import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author Cyrill
 */
public class testRun extends JFrame {

    public static FPSAnimator animator = null;
    static final int blocksize = 25;
    static final int widthInBlocks = 12;
    static final int heightInBlocks = 24;
    private static final int REFRESH_FPS = 8;    // Display refresh frames per second
    private static Renderer renderer;
    

    public static void main(String args[]) {
        final testRun app = new testRun();
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                app.setVisible(true);
            }
        });

        //start the animator

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                animator.start();
            }
        });

 

    }

    public testRun() {

        //set the JFrame title 
        super("Test Field");
//kill the process when the JFrame is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//best GL settings
        GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(glp); 
        GLCanvas glcanvas = new GLCanvas(caps);
        renderer = new Renderer(widthInBlocks * blocksize, heightInBlocks * blocksize, blocksize);

        glcanvas.addGLEventListener(renderer);
//add the GLCanvas just like we would any Component 
        getContentPane().add(glcanvas, BorderLayout.CENTER);
        setSize(widthInBlocks * blocksize, heightInBlocks * blocksize);
        //fixed FrameRate
        animator = new FPSAnimator(glcanvas, REFRESH_FPS, true);

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                // Use a dedicate thread to run the stop() to ensure that the
                // animator stops before program exddits.
                new Thread() {

                    @Override
                    public void run() {
                        animator.stop(); // stop the animator loop
                        System.exit(0);
                    }
                }.start();
            }
        });

        addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent ke) {
                int keyCode = ke.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_RIGHT:
                        renderer.moveRigth();
                        break;
                    case KeyEvent.VK_LEFT:
                        renderer.moveLeft();
                        break;
                    case KeyEvent.VK_DOWN:
                        renderer.moveDown();
                        break;

                    case KeyEvent.VK_UP:
                        renderer.moveUp();
                        break;

                    case KeyEvent.VK_G:
                        renderer.toggleGrid();
                        break;

                    case KeyEvent.VK_Y:
                        renderer.rotate();
                        break;
                    case KeyEvent.VK_X:
                        renderer.rotate();
                        break;
                }
            }
        });

        requestFocus();
    }
}