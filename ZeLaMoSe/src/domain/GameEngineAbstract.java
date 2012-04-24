/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.actions.ActionInterface;

/**
 *
 * @author chrigi
 */
abstract public class GameEngineAbstract extends SimulationStateAbstract implements ActionInterface{
    abstract public int getSessionID();
    abstract public void startGame();
    abstract public int getLevel();
    abstract public void setSimulationController(SimulationController simulationController);
    abstract public void setNickName(String nickName);
}