/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import domain.*;
import domain.fake.FakeNetworkHandler;
import domain.interfaces.SimulationStateInterface;
import network.client.NetworkHandlerImpl;
import network.SessionInformation;
import view.GameFieldJFrame;
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
        
        final GameFieldJFrame gamefield = new GameFieldJFrame();
        
        //TODO use the real network handler, setup other sessions
        OwnGameFieldJPanel panel = gamefield.getMainPanel();

        panel.setInputSampler(is);
        nH.setConnected();
        SimulationStateInterface ge = testrisController.getSession(15);
        assert(ge != null);
        assert(((GameEngine)ge).getSessionID() == 15);
        panel.setSimulation(ge);
        nH.setGameStarted();

        gamefield.setVisible(true);

    }
    
    public static void main(String[] args) {
        Tetris tetris = new Tetris();
        tetris.run();
    }
    
}
