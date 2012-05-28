package domain.fake;

import domain.BlockQueueInterface;
import domain.block.BlockAbstract;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Patrick Zenhäusern <pzenhaeu@hsr.ch>
 */
public class FakeBlockQueue implements BlockQueueInterface {

    public Queue<BlockAbstract> blocklist = new LinkedList<BlockAbstract>();

    @Override
    public BlockAbstract getNextBlock() {
        return blocklist.remove();
    }
}
