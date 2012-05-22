package domain;

import domain.block.BlockAbstract;
import domain.block.BlockType;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Cyrill Lam <clam@hsr.ch>
 */
public class BlockQueue implements BlockQueueInterface {

    private Random randomGenerator;
    private List<BlockType> blocksProbability;
    private int blockCounter;
    private long seed;

    public BlockQueue(long seed) {
        this(seed, true);
    }

    BlockQueue(long seed, boolean includeSpecialBlocks) {
        randomGenerator = new Random(seed);
        blockCounter = 0;
        this.seed = seed;
        fillBlockList(includeSpecialBlocks);
    }

    @Override
    public BlockAbstract getNextBlock() {
        BlockAbstract returnValue;
        returnValue = blocksProbability.get(randomGenerator.nextInt(blocksProbability.size())).createBlock(++blockCounter, seed);
        return returnValue;
    }

    private void fillBlockList(boolean includeSpecialBlocks) {
        blocksProbability = new ArrayList<BlockType>();
        for (BlockType blockType : BlockType.values()) {
            if ((includeSpecialBlocks || !blockType.isSpecialBlock())) {
                for (int i = 0; i < blockType.getProbability(); i++) {
                    blocksProbability.add(blockType);
                }
            }
        }
    }
}
