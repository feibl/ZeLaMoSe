/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author chrigi
 */
public interface GameEngineInterface extends ActionInterface, SimulationStateInterface{
    int sessionId();
}
