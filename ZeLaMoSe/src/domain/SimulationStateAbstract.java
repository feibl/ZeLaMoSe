package domain;

import domain.actions.Action;
import domain.block.BlockAbstract;
import java.util.Observable;

/**
 *
 * @author Christian Mollekopf <cmolleko@hsr.ch>
 */
abstract public class SimulationStateAbstract extends Observable {

    abstract public Action getSimulationState();

    abstract public int getScore();

    abstract public int getSessionID();

    abstract public void startGame();

    abstract public int getLevel();

    abstract public Action getlastActionForOthers();

    abstract public int getNumberOfJokers();

    abstract public int getRank();

    abstract public String getNickName();

    abstract public BlockAbstract getNextBlock();

    abstract public int getTotalRemovedLines();

    abstract public BlockAbstract getCurrentBlock();

    abstract public int getBlockCounter();

    public enum UpdateType {

        LASTACTION, ACTIONFOROTHERS, RANKING
    }
}
