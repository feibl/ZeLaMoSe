/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.actions.Action;
import domain.actions.ActionType;
import domain.actions.InputEvent;
import domain.fake.FakeInputSampler;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author chrigi
 */
public class StepGeneratorTest {
    int sessionId = 1;
    FakeInputSampler inputSampler;
    StepGenerator stepGenerator;
    public Collection<InputEvent> inputEvents = new ConcurrentLinkedQueue<InputEvent>();
    Component dummyComponent;
    
    @Before
    public void setUp() {
        inputSampler = new FakeInputSampler();
        stepGenerator = new StepGenerator(sessionId, inputSampler);
        dummyComponent = new Frame("test");
        
        inputEvents.add(new InputEvent(new KeyEvent(dummyComponent, 0, 0, 0, KeyEvent.VK_LEFT), 0));
        inputEvents.add(new InputEvent(new KeyEvent(dummyComponent, 1, 1, 0, KeyEvent.VK_Y), 0));
        inputEvents.add(new InputEvent(new KeyEvent(dummyComponent, 2, 2, 0, KeyEvent.VK_SPACE), 0));
        inputEvents.add(new InputEvent(new KeyEvent(dummyComponent, 3, 3, 0, KeyEvent.VK_J), 0));
        inputSampler.inputEvents = inputEvents;
    }
    
    @Test
    public void testStepGeneration() {
        stepGenerator.processStep();
        Step step = stepGenerator.getStep();
        
        assertEquals(step.getActions().get(0).getType(), ActionType.MOVE);
        assertEquals(step.getActions().get(1).getType(), ActionType.ROTATION);
        assertEquals(step.getActions().get(2).getType(), ActionType.HARDDROP);
        assertEquals(step.getActions().get(3).getType(), ActionType.CLEAR);       
    }
}
