/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.interfaces.StepProducerInterface;

/**
 *
 * @author chrigi
 */
public class FakeStepGenerator implements StepProducerInterface {
    public Step step;

    @Override
    public void niggasInParis() {
        //do noting
    }
    
    
    @Override
    public Step getStep() {
        return step;
    }
}
