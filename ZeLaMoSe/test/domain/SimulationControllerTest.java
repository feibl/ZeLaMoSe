/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.Fake.MockGameEngine;
import domain.actions.ActionType;
import domain.actions.Action;
import domain.actions.MoveAction;
import domain.actions.RotateAction;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author chrigi
 */
public class SimulationControllerTest {
  int sessionId = 666;
  int sessionId2 = 667;
  int sessionId3 = 668;
  String name = "name";
  SimulationController instance;
  MockGameEngine engine1;
  MockGameEngine engine2;
  MockGameEngine engine3;

  public SimulationControllerTest() {
  }
  
  @Before
  public void setUp() {
      instance = new SimulationController();
      engine1 = new MockGameEngine(sessionId);
      instance.addSession(sessionId, name, engine1);
      engine2 = new MockGameEngine(sessionId2);
      instance.addSession(sessionId2, name, engine2);
      engine3 = new MockGameEngine(sessionId3);
      instance.addSession(sessionId3, name, engine3);
  }
  
  @After
  public void tearDown() {
  }
  
  @Test
  public void testGetSimulation() {
      System.out.println("getSimulation");
      //assertEquals(instance.getSimulation(0), null);
      assertEquals(instance.getSimulation(sessionId), engine1);
      assertEquals(instance.getSimulation(sessionId2), engine2);
      assertEquals(instance.getSimulation(sessionId3), engine3);
  }
  
  Step createStep(int id, Action action, int seq) {
      Step step = new Step(seq, id);
      
      step.addAction(action);
      return step;
  }

  /**
   * Test of addStep method, of class SimulationController.
   */
  @Test
  public void testAddStep() {
      System.out.println("addStep");
      
      Action action = new RotateAction(10, RotateAction.Direction.LEFT);
      instance.addStep(createStep(sessionId, action, 0));
      instance.addStep(createStep(sessionId2, action, 0));
      instance.addStep(createStep(sessionId3, action, 0));
      
      //Nothin simulated so far
      assertNull(engine1.getLastAction());
      assertNull(engine2.getLastAction());
      assertNull(engine3.getLastAction());
      
//      //Wrong step simulation doesn't trigger simulation
//      instance.simulateStep(1);
//      assertNull(engine1.getLastAction());
//      assertNull(engine2.getLastAction());
//      assertNull(engine3.getLastAction());
      
      //Simulation adds getActions to engines
      instance.simulateStep(0);
      assertEquals(action, engine1.getLastAction());
      assertEquals(action, engine2.getLastAction());
      assertEquals(action, engine3.getLastAction());
  }
  
  
  @Test
  public void testActionSorting() {
      System.out.println("testActionSorting");
      
      for (int i = 0; i < 8; i++) {
      
        Action action1 = new RotateAction(10, RotateAction.Direction.LEFT);
        Action action2 = new RotateAction(30, RotateAction.Direction.RIGHT);
        Action action3 = new MoveAction(20, MoveAction.Direction.LEFT, 1);
        instance.addStep(createStep(sessionId, action1, i));
        instance.addStep(createStep(sessionId2, action2, i));
        instance.addStep(createStep(sessionId3, action3, i));
        if (i == 0) {
            assertNull(engine1.getLastAction());
            assertNull(engine2.getLastAction());
            assertNull(engine3.getLastAction());
        }
        instance.simulateStep(i);
        assertEquals(action1, engine1.getLastAction());
        assertEquals(action2, engine2.getLastAction());
        assertEquals(action3, engine3.getLastAction());
    }
  }
}
