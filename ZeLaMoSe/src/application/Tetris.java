/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import domain.*;
import domain.fake.FakeNetworkHandler;
import domain.interfaces.SimulationStateInterface;
import java.util.Observable;
import java.util.Observer;
import network.client.NetworkHandlerImpl;
import network.SessionInformation;
import view.GameFieldJFrame;
import view.OwnGameFieldJPanel;

/**
 *
 * @author chrigi
 */
public class Tetris implements Observer {

    private TetrisController testrisController;
    private InputSampler is = new InputSampler();
    FakeNetworkHandler nH;

    public Tetris() {
        nH = new FakeNetworkHandler(new SessionInformation(15, "nicky"));
        testrisController = new TetrisController(new SimulationController(), nH, new StepGeneratorImpl(is));
    }

    public void run() {
        testrisController.addObserver(this);
        nH.setConnected();
        nH.setGameStarted();
    }

    public static void main(String[] args) {
        Tetris tetris = new Tetris();
        tetris.run();
    }

    @Override
    public void update(Observable o, Object o1) {
        switch ((TetrisController.UpdateType) o1) {
            case GAME_STARTED:
                final GameFieldJFrame gamefield = testrisController.createGameFieldFrame();
                gamefield.setVisible(true);
                break;
        }
    }
}
