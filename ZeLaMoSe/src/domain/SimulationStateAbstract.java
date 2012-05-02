/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.actions.Action;
import java.util.Observable;

/**
 *
 * @author Christian Mollekopf <cmolleko@hsr.ch>
 */
abstract public class SimulationStateAbstract extends Observable {
    abstract public Action getSimulationState();

    abstract public void setNumberOfJokers(int numberOfJokers) ;
    public enum UpdateType {
        LASTACTION, ACTIONFOROTHERS
    }
}
