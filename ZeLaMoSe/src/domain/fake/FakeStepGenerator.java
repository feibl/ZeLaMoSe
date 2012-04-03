/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.fake;

import domain.Step;
import domain.StepGenerator;
import domain.TetrisController;
import domain.interfaces.StepProducerInterface;
import network.NetworkHandler;

/**
 *
 * @author chrigi
 */
public class FakeStepGenerator extends StepGenerator {
    public Step step;

    @Override
    public void setSessionID(int sessionID) {
        //do nothing
    }    

    @Override
    public void niggasInParis() {
        setChanged();
        notifyObservers(TetrisController.UpdateType.STEP);
    }
    
    @Override
    public Step getStep() {
        return step;
    }
}
