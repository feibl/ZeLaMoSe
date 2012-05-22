package domain;

import domain.actions.ActionInterface;

/**
 *
 * @author Christian Mollekopf <cmolleko@hsr.ch>
 */
abstract public class GameEngineAbstract extends SimulationStateAbstract implements ActionInterface {

    abstract public void setNickName(String nickName);

    abstract public void setRank(int rank);

    abstract public void setLevel(int currentHighestLevel);
}