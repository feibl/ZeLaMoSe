/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.actions.Action;
import domain.fake.FakeStepGenerator;
import domain.fake.FakeNetworkHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import domain.actions.MoveAction;
import java.util.Observable;
import java.util.Observer;
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
        nH = new FakeNetworkHandler(new SessionInformation(sessionID, "test"));
        sG = new FakeStepGenerator();
        tC = new TetrisController(sC, nH, sG);
        tC.autorun = false;
    }
  
    @After
    public void tearDown() {
    }
    
    Step createStep(int i, int session) {
        Step s = new Step(i, session);
        s.addAction(new MoveAction(10, MoveAction.Direction.LEFT, 1));
        s.addAction(new MoveAction(20, MoveAction.Direction.LEFT, 1));
        s.addAction(new MoveAction(30, MoveAction.Direction.LEFT, 1));
        s.addAction(new MoveAction(40, MoveAction.Direction.DOWN, 1));
        return s;
    }
  
    @Test
    public void testSimulation() {
        assertTrue(nH.getSessionList().containsKey(sessionID));
        assertNotNull(nH.getSessionList().get(sessionID));
        nH.setConnected();
        nH.setGameStarted();
        SimulationStateAbstract gE = sC.getSimulationStateInterface(sessionID);
        assertNotNull(gE);
        GameEngine gameEngine = (GameEngine)gE;
        assertEquals(gameEngine.getSessionID(), sessionID);
        gameEngine.print();
        
        for (int i = 0; i < 10; i++) {
//            System.out.println(i+"############################################");
            sG.step = createStep(i, sessionID);
            assertEquals(sG.step.getSequenceNumber(), i);
            tC.runStep();
            assertEquals(nH.lastStep, sG.step);
        }

        gameEngine.print();
    }
    
    class Tester implements Observer {
        GameEngine ge;
        Tester(GameEngine g) {
            g.addObserver(this);
            ge = g;
        }

        @Override
        public void update(Observable o, Object o1) {
            assertEquals(o, ge);
            //Action s = ge.getSimulationState();
        }
    }
    
    @Test
    public void testSimulationWithMultipleSessions() {
        nH.setConnected();
        nH.getSessionList().put(4, "session4");
        nH.getSessionList().put(5, "session5");
        nH.getSessionList().put(6, "session6");
        nH.setGameStarted();
        
        GameEngine gE1 = (GameEngine)sC.getSimulationStateInterface(sessionID);
        new Tester(gE1);
        GameEngine gE2 = (GameEngine)sC.getSimulationStateInterface(4);
        new Tester(gE2);
        GameEngine gE3 = (GameEngine)sC.getSimulationStateInterface(5);
        new Tester(gE3);
        GameEngine gE4 = (GameEngine)sC.getSimulationStateInterface(6);
        new Tester(gE4);
        
        for (int i = 0; i < 10; i++) {
            sG.step = createStep(i, sessionID);
            tC.runStep();
            //assertEquals(nH.lastStep, sG.step);
            nH.addRemoteStep(createStep(i, 4));
            nH.addRemoteStep(createStep(i, 5));
            nH.addRemoteStep(createStep(i, 6));
            if (i > 0) { //Start of simulations
                
            }
        }
        
        GameEngine gE = (GameEngine)sC.getSimulationStateInterface(sessionID);
        assertNotNull(gE);

        
    } 
}
