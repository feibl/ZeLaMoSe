/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import domain.Fake.FakeGameEngine;
import domain.InputSampler;
import domain.SimulationController;
import domain.StepGeneratorImpl;
import domain.TetrisController;
import domain.fake.FakeNetworkHandler;
import java.awt.KeyboardFocusManager;
import network.NetworkHandlerImpl;
import network.SessionInformation;
import view.OwnGameFieldJPanel;

/**
 *
 * @author chrigi
 */
public class Tetris {
    
    private TetrisController testrisController;
    private InputSampler is = new InputSampler();
    FakeNetworkHandler nH;
    
    public Tetris() {
        nH = new FakeNetworkHandler();
        nH.localSession = new SessionInformation(15, "nicky");
        testrisController = new TetrisController(new SimulationController(), nH, new StepGeneratorImpl(is));
    }
    
    public void run() {
        OwnGameFieldJPanel panel = new OwnGameFieldJPanel();
        panel.setInputSampler(is);
        panel.setVisible(true);
        //nH.setConnected();
        //panel.setSimulation(testrisController.getSession(15));
        //nH.setGameStarted(); 
    }
    
    public static void main(String[] args) {
        Tetris tetris = new Tetris();
        tetris.run();
    }
    
}
