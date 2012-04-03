/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import domain.SimulationController;
import domain.TetrisController;
import network.NetworkHandlerImpl;
import view.OwnGameFieldJPanel;

/**
 *
 * @author chrigi
 */
public class Tetris {
    
    private TetrisController testrisController;

    public Tetris() {
        testrisController = new TetrisController(new SimulationController(), new NetworkHandlerImpl());
        
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
