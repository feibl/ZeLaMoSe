package domain;

import domain.actions.Action;
import domain.actions.MoveAction;
import domain.actions.RotateAction;
import domain.fake.FakeGameEngine;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Christian Mollekopf <cmolleko@hsr.ch>
 */
public class SimulationControllerTest {

    int sessionId = 666;
    int sessionId2 = 667;
    int sessionId3 = 668;
    String name = "name";
    SimulationController instance;
    FakeGameEngine engine1;
    FakeGameEngine engine2;
    FakeGameEngine engine3;

    public SimulationControllerTest() {
    }

    @Before
    public void setUp() {
        instance = new SimulationController(false);
        engine1 = new FakeGameEngine(sessionId);
        instance.addSession(sessionId, name, engine1);
        engine2 = new FakeGameEngine(sessionId2);
        instance.addSession(sessionId2, name, engine2);
        engine3 = new FakeGameEngine(sessionId3);
        instance.addSession(sessionId3, name, engine3);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetSimulation() {
        System.out.println("getSimulation");
        //assertEquals(instance.getSimulationStateInterface(0), null);
        assertEquals(instance.getSimulationStateInterface(sessionId), engine1);
        assertEquals(instance.getSimulationStateInterface(sessionId2), engine2);
        assertEquals(instance.getSimulationStateInterface(sessionId3), engine3);
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

    class Tester implements Observer {

        public Action lastAction;
        public ArrayList<Action> expected;
        public ArrayList<FakeGameEngine> expectedEngine;

        Tester() {
        }

        @Override
        public void update(Observable o, Object o1) {
            FakeGameEngine g = (FakeGameEngine) o;
            lastAction = g.getSimulationState();
            assertTrue(!expected.isEmpty());

            if (g != expectedEngine.get(0)) {
                System.out.println("error: " + expected);
                System.out.println("error: " + expectedEngine);
            }

            assertEquals(g, expectedEngine.remove(0));

            Action e = expected.remove(0);

            assertEquals(e, lastAction);
        }
    }

    @Test
    public void testActionSorting() {
        System.out.println("testActionSorting");
        Tester t = new Tester();
        engine1.addObserver(t);
        engine2.addObserver(t);
        engine3.addObserver(t);

        for (int i = 0; i < 8; i++) {

            Action action1 = new RotateAction(10, RotateAction.Direction.LEFT);
            Action action2 = new RotateAction(30, RotateAction.Direction.RIGHT);
            Action action3 = new MoveAction(20, MoveAction.Direction.LEFT, 1);

            t.expected = new ArrayList<Action>();
            t.expectedEngine = new ArrayList<FakeGameEngine>();

            t.expected.add(action1);
            t.expectedEngine.add(engine1);

            t.expected.add(action3);
            t.expectedEngine.add(engine3);

            t.expected.add(action2);
            t.expectedEngine.add(engine2);

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

            assertEquals(0, t.expected.size());
        }
    }

    @Test
    public void testSameTimeStampActionSorting() {
        SimulationController s1 = new SimulationController(false);
        SimulationController s2 = new SimulationController(false);

        final List<Action> actionList1 = new ArrayList<Action>();
        final List<Action> actionList2 = new ArrayList<Action>();

        class TestGameEngine extends FakeGameEngine {

            private final List<Action> actionList;

            public TestGameEngine(int sessionId, List<Action> actionList) {
                super(sessionId);
                this.actionList = actionList;
            }

            @Override
            public void handleAction(Action action) {
                actionList.add(action);
            }
        }

        //Different order of adding sessions and steps
        s1.addSession(sessionId, name, new TestGameEngine(sessionId, actionList1));
        s1.addSession(sessionId2, name, new TestGameEngine(sessionId2, actionList1));

        s2.addSession(sessionId2, name, new TestGameEngine(sessionId2, actionList2));
        s2.addSession(sessionId, name, new TestGameEngine(sessionId, actionList2));


        Step step1 = new Step(0, sessionId);
        Step step2 = new Step(0, sessionId2);

        for (int i = 0; i < 1000; i++) {
            step1.addAction(new RotateAction(10, RotateAction.Direction.LEFT));
            step2.addAction(new RotateAction(10, RotateAction.Direction.RIGHT));
        }

        s1.addStep(step1);
        s1.addStep(step2);

        s2.addStep(step2);
        s2.addStep(step1);

        s1.simulateStep(0);
        s2.simulateStep(0);

        assertTrue(actionList1.size() == actionList2.size());
        assertEquals(2000, actionList1.size());
        assertEquals(2000, actionList2.size());

        for (int i = 0; i < actionList1.size(); i++) {
            assertTrue(actionList1.get(i) == actionList2.get(i));
        }
    }
}
