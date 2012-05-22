package domain;

import domain.actions.InputEvent;
import java.util.Collection;

/**
 *
 * @author Christian Mollekopf <cmolleko@hsr.ch>
 */
public interface InputSamplerInterface {

    public Collection<InputEvent> getAndRemoveAll();
}
