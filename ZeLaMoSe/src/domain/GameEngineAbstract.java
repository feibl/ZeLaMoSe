/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.actions.ActionInterface;
import domain.actions.GarbageLineAction;

/**
 *
 * @author Christian Mollekopf <cmolleko@hsr.ch>
 */
abstract public class GameEngineAbstract extends SimulationStateAbstract implements ActionInterface{
    abstract public int getSessionID();
    abstract public void startGame();
    abstract public int getLevel();
    abstract public void setNickName(String nickName);
    abstract public GarbageLineAction getlastGarbageLineAction();
}