package domain.fake;

import domain.BlockQueue;
import domain.block.BlockAbstract;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Patrick Zenhäusern <pzenhaeu@hsr.ch>
 */
public class FakeBlockQueue extends BlockQueue {

    public Queue<BlockAbstract> blocklist = new LinkedList<BlockAbstract>();

    public FakeBlockQueue() {
        super(0);
    }

    @Override
    public BlockAbstract getNextBlock() {
        return blocklist.remove();
    }
}
