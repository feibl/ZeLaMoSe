/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.actions.InputEvent;
import java.util.Collection;

/**
 *
 * @author chrigi
 */
public interface InputSamplerInterface {
    public Collection<InputEvent> getAndRemoveAll();
}
