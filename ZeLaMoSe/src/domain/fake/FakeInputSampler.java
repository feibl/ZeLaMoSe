package domain.fake;

import domain.InputSampler;
import domain.actions.InputEvent;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author Christian Mollekopf <cmolleko@hsr.ch>
 */
public class FakeInputSampler extends InputSampler {

    public Collection<InputEvent> inputEvents = new ConcurrentLinkedQueue<InputEvent>();

    @Override
    public Collection<InputEvent> getAndRemoveAll() {
        return inputEvents;
    }
}
