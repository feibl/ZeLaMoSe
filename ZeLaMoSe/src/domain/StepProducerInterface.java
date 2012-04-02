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
  
  public Step getStep();
  //Called every 50ms
  public void runStep();
}
