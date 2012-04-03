/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.interfaces;

import domain.actions.Action;
import java.util.Observable;

/**
 *
 * @author chrigi
 */
abstract public class SimulationStateInterface extends Observable {
    abstract public Action getSimulationState();
}
