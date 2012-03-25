/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author chrigi
 */
public interface StepProducerInterface {
  
    /*
    * Register StepConsumer
    */
    public void addListener(StepInterface listener);
  
}
