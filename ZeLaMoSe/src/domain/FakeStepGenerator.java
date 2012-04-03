/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author chrigi
 */
public class FakeStepGenerator implements StepProducerInterface {
    public Step step;

    @Override
    public void runStep() {
        //do noting
    }
    
    
    @Override
    public Step getStep() {
        return step;
    }
}
