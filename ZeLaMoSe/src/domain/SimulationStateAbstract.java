/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.actions.Action;
import java.util.Observable;

/**
 *
 * @author chrigi
 */
abstract public class SimulationStateAbstract extends Observable {
    abstract public Action getSimulationState();
}
