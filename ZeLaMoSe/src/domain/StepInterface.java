/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.rmi.RemoteException;

/**
 *
 * @author chrigi
 */
public interface StepInterface {
    public void addStep(Step step) throws RemoteException;
}
