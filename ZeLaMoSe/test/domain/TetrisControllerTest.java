/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.fake.FakeStepGenerator;
import network.FakeNetworkHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import domain.actions.InputEvent;
import domain.interfaces.SimulationStateInterface;

/**
 *
 * @author chrigi
 */
public class TetrisControllerTest {
    
    TetrisController tC;
    SimulationController sC;
    FakeNetworkHandler nH;
    FakeStepGenerator sG;
    int sessionID = 3;

    public TetrisControllerTest() {
        
    }
    
    
    @Before
    public void setUp() {
        sC = new SimulationController();
        nH = new FakeNetworkHandler();
        sG = new FakeStepGenerator();
        sG.step = new Step(0, sessionID);
        tC = new TetrisController(sC, nH, sG);
    }
  
    @After
    public void tearDown() {
    }
  
    @Test
    public void testSimulation() {
        nH.setConnected();
        SimulationStateInterface gE = sC.getSimulation(sessionID);
        
        
        
        tC.runStep();
        
    } 
}
