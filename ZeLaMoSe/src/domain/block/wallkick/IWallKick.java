package domain.block.wallkick;

import domain.block.BlockAbstract;

/**
 *
 * @author Patrick Zenhäusern <pzenhaeu@hsr.ch>
 */
public class IWallKick extends WallKickAbstract {

    @Override
    public void Test1(BlockAbstract block) {
    }

    @Override
    public void Test2(BlockAbstract block) {
        switch (block.getBlockRotationState()) {
            case r0r90:
            case r270r180:
                block.setX(block.getX() - 2);
                block.setY(block.getY() + 0);
                break;
            case r90r0:
            case r180r270:
                block.setX(block.getX() + 2);
                block.setY(block.getY() + 0);
                break;
            case r90r180:
            case r0r270:
                block.setX(block.getX() - 1);
                block.setY(block.getY() + 0);
                break;
            case r180r90:
            case r270r0:
                block.setX(block.getX() + 1);
                block.setY(block.getY() + 0);
                break;

        }
    }

    @Override
    public void Test3(BlockAbstract block) {
        switch (block.getBlockRotationState()) {
            case r0r90:
            case r270r180:
                block.setX(block.getX() + 1);
                block.setY(block.getY() + 0);
                break;
            case r90r0:
            case r180r270:
                block.setX(block.getX() - 1);
                block.setY(block.getY() + 0);
                break;
            case r90r180:
            case r0r270:
                block.setX(block.getX() + 2);
                block.setY(block.getY() + 0);
                break;
            case r180r90:
            case r270r0:
                block.setX(block.getX() - 2);
                block.setY(block.getY() + 0);
                break;
        }
    }

    @Override
    public void Test4(BlockAbstract block) {
        switch (block.getBlockRotationState()) {
            case r0r90:
            case r270r180:
                block.setX(block.getX() - 2);
                block.setY(block.getY() - 1);
                break;
            case r90r0:
            case r180r270:
                block.setX(block.getX() + 2);
                block.setY(block.getY() + 1);
                break;
            case r90r180:
            case r0r270:
                block.setX(block.getX() - 1);
                block.setY(block.getY() + 2);
                break;
            case r180r90:
            case r270r0:
                block.setX(block.getX() + 1);
                block.setY(block.getY() - 2);
                break;
        }
    }

    @Override
    public void Test5(BlockAbstract block) {
        switch (block.getBlockRotationState()) {
            case r0r90:
            case r270r180:
                block.setX(block.getX() + 1);
                block.setY(block.getY() + 2);
                break;
            case r90r0:
            case r180r270:
                block.setX(block.getX() - 1);
                block.setY(block.getY() - 2);
                break;
            case r90r180:
            case r0r270:
                block.setX(block.getX() + 2);
                block.setY(block.getY() - 1);
                break;
            case r180r90:
            case r270r0:
                block.setX(block.getX() - 2);
                block.setY(block.getY() + 1);
                break;
        }
    }
}
