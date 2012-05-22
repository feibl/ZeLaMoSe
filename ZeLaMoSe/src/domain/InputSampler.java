package domain;

import domain.actions.InputEvent;
import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author Patrick Zenhäusern <pzenhaeu@hsr.ch>
 */
public class InputSampler implements KeyEventDispatcher, InputSamplerInterface {

    private ConcurrentLinkedQueue<InputEvent> keyEventQueue;

    public InputSampler() {
        keyEventQueue = new ConcurrentLinkedQueue<InputEvent>();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        if (e.getID() == KeyEvent.KEY_PRESSED) {
            keyEventQueue.add(new InputEvent(e, System.nanoTime()));
        }
        return false;
    }

    @Override
    public Collection<InputEvent> getAndRemoveAll() {
        Collection<InputEvent> c = new ArrayList<InputEvent>(keyEventQueue);
        keyEventQueue.removeAll(c);
        return c;
    }
}
