/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.fake.FakeStepGenerator;
import domain.fake.FakeNetworkHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import domain.actions.InputEvent;
import domain.interfaces.SimulationStateInterface;
import network.SessionInformation;


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
        nH.localSession = new SessionInformation(3, "test");
        sG = new FakeStepGenerator();
        tC = new TetrisController(sC, nH, sG);
    }
  
    @After
    public void tearDown() {
    }
  
    @Test
    public void testSimulation() {
        nH.setConnected();
        SimulationStateInterface gE = sC.getSimulation(sessionID);
        GameEngine gameEngine = (GameEngine)gE;
        assertEquals(gameEngine.getSessionID(), 3);
        
        //sG.step = new Step(0, sessionID);
        //tC.runStep();
        
    } 
}
