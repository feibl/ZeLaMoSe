/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.interfaces;

import domain.Step;
import java.rmi.RemoteException;

/**
 *
 * @author chrigi
 */
public interface StepInterface {
    public void addStep(Step step);
}
