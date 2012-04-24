/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.fake;

import domain.InputSampler;
import domain.Step;
import domain.StepGeneratorAbstract;
import domain.TetrisController;
import domain.StepProducerInterface;
import network.client.NetworkHandler;

/**
 *
 * @author chrigi
 */
public class FakeStepGenerator extends StepGeneratorAbstract {
    public Step step;

    @Override
    public void setSessionID(int sessionID) {
        //do nothing
    }    

    @Override
    public void processStep() {
        setChanged();
        notifyObservers(TetrisController.UpdateType.STEP);
    }
    
    @Override
    public Step getStep() {
        return step;
    }

    @Override
    public InputSampler getInputSampler() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
