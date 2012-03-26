/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.actions.ActionType;
import domain.actions.Action;
import domain.actions.RotateAction;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author chrigi
 */
public class SimulationControllerTest {
  int sessionId = 666;
  String name = "name";
  SimulationController instance;
  MockGameEngine engine;

  public SimulationControllerTest() {
  }
  
  @Before
  public void setUp() {
      instance = new SimulationController();
      engine = new MockGameEngine(sessionId);
      instance.addSession(sessionId, name, engine);
  }
  
  @After
  public void tearDown() {
  }
  
  @Test
  public void testGetSimulation() {
      System.out.println("getSimulation");
      assertEquals(instance.getSimulation(0), null);
      assertEquals(instance.getSimulation(sessionId), engine);
  }

  /**
   * Test of addStep method, of class SimulationController.
   */
  @Test
  public void testAddStep() {
      System.out.println("addStep");
      Step step = new Step(0, sessionId);
      Action action = new RotateAction(10, RotateAction.Direction.LEFT);
      step.addAction(action);
      instance.addStep(step);
      assertEquals(null, engine.getLastAction());
      //instance.simulateStep(1);
      //assertEquals(null, engine.getLastAction());
      instance.simulateStep(0);
      assertEquals(action, engine.getLastAction());
  }
}
