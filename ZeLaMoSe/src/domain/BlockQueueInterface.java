package domain;

import domain.block.BlockAbstract;

/**
 *
 * @author Patrick Zenhäusern <pzenhaeu@hsr.ch>
 */
public interface BlockQueueInterface {

    public BlockAbstract getNextBlock();
}
