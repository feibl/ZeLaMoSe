/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import domain.SimulationController;
import domain.TestrisController;
import network.NetworkHandlerImpl;
import view.OwnGameFieldJPanel;

/**
 *
 * @author chrigi
 */
public class Tetris {
    
    TestrisController testrisController;

    public Tetris() {
        testrisController = new TestrisController(new SimulationController(), new NetworkHandlerImpl());
        
    }
    
    public void run() {
        OwnGameFieldJPanel panel = new OwnGameFieldJPanel();
        panel.setVisible(true);
    }
    
    public static void main(String[] args) {
        Tetris tetris = new Tetris();
        tetris.run();
    }
    
}
