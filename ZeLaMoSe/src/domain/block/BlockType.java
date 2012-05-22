package domain.block;

import java.lang.reflect.Constructor;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Patrick Zenhäusern <pzenhaeu@hsr.ch>
 */
public enum BlockType {

    I(IBlock.class, 100, false), J(JBlock.class, 100, false), L(LBlock.class, 100, false),
    O(OBlock.class, 100, false), S(SBlock.class, 100, false), T(TBlock.class, 100, false),
    Z(ZBlock.class, 100, false), GARBAGE(GarbageBlock.class, 0, true), MIRROR(MirrorBlock.class, 10, true),
    DARK(DarkBlock.class, 10, true);
    private final Class className;
    private final int probability;
    private boolean specialBlock;

    public BlockAbstract createBlock(int blockNumber, long seed) {
        try {
            Constructor c = className.getConstructor(new Class[]{Integer.TYPE, Long.TYPE});
            return (BlockAbstract) (c.newInstance(new Object[]{blockNumber, seed}));
        } catch (Exception ex) {
            Logger.getLogger(BlockType.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    BlockType(Class cn, int probability, boolean specialBlock) {
        this.className = cn;
        this.probability = probability;
        this.specialBlock = specialBlock;
    }

    public boolean isSpecialBlock() {
        return specialBlock;
    }

    public int getProbability() {
        return probability;
    }
}
