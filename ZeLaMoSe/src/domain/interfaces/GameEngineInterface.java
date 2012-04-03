/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.interfaces;

/**
 *
 * @author chrigi
 */
abstract public class GameEngineInterface extends SimulationStateInterface implements ActionInterface{
    abstract public int getSessionID();
    abstract public void startGame();
}
