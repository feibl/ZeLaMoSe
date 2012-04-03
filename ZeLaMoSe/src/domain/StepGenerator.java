/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.interfaces.StepProducerInterface;
import java.util.Observable;

/**
 *
 * @author chrigi
 */
abstract public class StepGenerator extends Observable implements StepProducerInterface {
    abstract public void setSessionID(int sessionID);
//    
//    @Override
//    abstract public void niggasInParis();
}
