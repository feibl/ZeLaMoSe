/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import network.FakeNetworkHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import domain.actions.InputEvent;

/**
 *
 * @author chrigi
 */
public class TetrisControllerTest {
    
    TestrisController tC;
    SimulationController sC;
    FakeNetworkHandler nH;
    FakeStepGenerator sG;
    int sessionID = 3

    public TetrisControllerTest() {
        
    }
    
    
    @Before
    public void setUp() {
        sC = new SimulationController();
        nH = new FakeNetworkHandler();
        sG = new FakeStepGenerator();
        sG.step = new Step(seqNum, sessionID)
        tC = new TestrisController(sC, nH, sG);
    }
  
    @After
    public void tearDown() {
    }
  
    @Test
    public void testSimulation() {
        nH.setConnected();
        SimulationStateInterface gE = sC.getSimulation(sessionID);
        
        sG.addAction(new InputEvent( 5000));
        
        tC.runStep();
        
    } 
}
