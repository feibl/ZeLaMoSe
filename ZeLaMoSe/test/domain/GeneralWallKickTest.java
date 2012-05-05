/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.actions.MoveAction;
import domain.actions.RotateAction;
import domain.block.LBlock;
import org.junit.Test;

/**
 *
 * @author Patrick Zenhäusern <pzenhaeu@hsr.ch>
 */
public class GeneralWallKickTest  extends WallKickTest{

    @Override
    protected void fillInFakes() {
        fakeQueue.blocklist.add(new LBlock(1));
        fakeQueue.blocklist.add(new LBlock(2));
    }

    @Override
    protected void moveBlockToStartPositionr0r90() {
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
    }

    @Override
    @Test
    public void WallKickTest1r0r90() {
        moveBlockToStartPositionr0r90();
        doRotationr0r90();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 19);
        assertEqualBothGrids();
    }

    @Override
    @Test
    public void WallKickTest2r0r90() {
        moveBlockToStartPositionr0r90();
        actualGrid[6][19] = dummyBlock;
        doRotationr0r90();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 19);
        assertEqualBothGrids();
    }

    @Override
    @Test
    public void WallKickTest3r0r90() {
        moveBlockToStartPositionr0r90();
        actualGrid[4][19] = dummyBlock;
        actualGrid[6][19] = dummyBlock;
        doRotationr0r90();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 22);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 20);
        assertEqualBothGrids();
    }

    @Override
    @Test
    public void WallKickTest4r0r90() {
         moveBlockToStartPositionr0r90();
        actualGrid[4][19] = dummyBlock;
        actualGrid[4][22] = dummyBlock;
        actualGrid[6][19] = dummyBlock;
        doRotationr0r90();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 18);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 17);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 17);
        assertEqualBothGrids();
    }

    @Override
    @Test
    public void WallKickTest5r0r90() {
         moveBlockToStartPositionr0r90();
        actualGrid[4][22] = dummyBlock;
        actualGrid[5][19] = dummyBlock;
        actualGrid[6][19] = dummyBlock;
        doRotationr0r90();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 18);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 17);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 17);
        assertEqualBothGrids();
    }

    @Override
    @Test
    public void WallKickCollisionr0r90() {
         moveBlockToStartPositionr0r90();
        actualGrid[4][22] = dummyBlock;
        actualGrid[5][19] = dummyBlock;
        actualGrid[4][19] = dummyBlock;
        actualGrid[6][19] = dummyBlock;
        doRotationr0r90();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 21);
        assertEqualBothGrids();
    }

    @Override
    protected void moveBlockToStartPositionr90r0() {
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.RIGHT));
    }

    @Override
    @Test
    public void WallKickTest1r90r0() {
        moveBlockToStartPositionr90r0();
        doRotationr90r0();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 21);
        assertEqualBothGrids();
    }

    @Override
    @Test
    public void WallKickTest2r90r0() {
        moveBlockToStartPositionr90r0();
        actualGrid[6][21] = dummyBlock;
        doRotationr90r0();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 21);
        assertEqualBothGrids();
    }

    @Override
    @Test
    public void WallKickTest3r90r0() {
        moveBlockToStartPositionr90r0();
        actualGrid[6][20] = dummyBlock;
        doRotationr90r0();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 20);
        assertEqualBothGrids();
    }

    @Override
    @Test
    public void WallKickTest4r90r0() {
        moveBlockToStartPositionr90r0();
        actualGrid[6][20] = dummyBlock;
        actualGrid[7][20] = dummyBlock;
        doRotationr90r0();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 22);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 22);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 22);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 23);
        assertEqualBothGrids();
    }

    @Override
    @Test
    public void WallKickTest5r90r0() {
        moveBlockToStartPositionr90r0();
        actualGrid[6][20] = dummyBlock;
        actualGrid[7][20] = dummyBlock;
        actualGrid[4][22] = dummyBlock;
        doRotationr90r0();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 22);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 22);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 22);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 23);
        assertEqualBothGrids();
    }

    @Override
    @Test
    public void WallKickCollisionr90r0() {
        moveBlockToStartPositionr90r0();
        actualGrid[6][20] = dummyBlock;
        actualGrid[7][20] = dummyBlock;
        actualGrid[4][22] = dummyBlock;
        actualGrid[5][22] = dummyBlock;
        doRotationr90r0();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 19);
        assertEqualBothGrids();
    }

    @Override
    protected void moveBlockToStartPositionr90r180() {
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.RIGHT));
    }

    @Override
    @Test
    public void WallKickTest1r90r180() {
        moveBlockToStartPositionr90r180();
        doRotationr90r180();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 19);
        assertEqualBothGrids();
    }

    @Override
    @Test
    public void WallKickTest2r90r180() {
        moveBlockToStartPositionr90r180();
        actualGrid[4][19] = dummyBlock;
        doRotationr90r180();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 19);
        assertEqualBothGrids();
    }

    @Override
    @Test
    public void WallKickTest3r90r180() {
        moveBlockToStartPositionr90r180();
        actualGrid[4][19] = dummyBlock;
        actualGrid[7][20] = dummyBlock;
        doRotationr90r180();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 18);
        assertEqualBothGrids();
    }

    @Override
    @Test
    public void WallKickTest4r90r180() {
        moveBlockToStartPositionr90r180();
        actualGrid[4][19] = dummyBlock;
        actualGrid[7][19] = dummyBlock;
        actualGrid[7][20] = dummyBlock;
        doRotationr90r180();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 22);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 22);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 22);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 21);
        assertEqualBothGrids();
    }

    @Override
    @Test
    public void WallKickTest5r90r180() {
        moveBlockToStartPositionr90r180();
        actualGrid[4][19] = dummyBlock;
        actualGrid[4][22] = dummyBlock;
        actualGrid[7][19] = dummyBlock;
        actualGrid[7][20] = dummyBlock;
        doRotationr90r180();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 22);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 22);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 22);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 21);
        assertEqualBothGrids();
    }

    @Override
    @Test
    public void WallKickCollisionr90r180() {
        moveBlockToStartPositionr90r180();
        actualGrid[4][19] = dummyBlock;
        actualGrid[4][22] = dummyBlock;
        actualGrid[7][19] = dummyBlock;
        actualGrid[7][20] = dummyBlock;
        actualGrid[7][22] = dummyBlock;
        doRotationr90r180();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 19);
        assertEqualBothGrids();
    }

    @Override
    protected void moveBlockToStartPositionr180r90() {
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.RIGHT));
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.RIGHT));
    }

    @Override
    @Test
    public void WallKickTest1r180r90() {
        moveBlockToStartPositionr180r90();
        doRotationr180r90();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 19);
        assertEqualBothGrids();
    }

    @Override
    @Test
    public void WallKickTest2r180r90() {
        moveBlockToStartPositionr180r90();
        actualGrid[6][19] = dummyBlock;
        doRotationr180r90();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 19);
        assertEqualBothGrids();
    }

    @Override
    @Test
    public void WallKickTest3r180r90() {
        moveBlockToStartPositionr180r90();
        actualGrid[5][19] = dummyBlock;
        actualGrid[6][19] = dummyBlock;
        doRotationr180r90();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 22);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 20);
        assertEqualBothGrids();
    }

    @Override
    @Test
    public void WallKickTest4r180r90() {
        moveBlockToStartPositionr180r90();
        actualGrid[4][22] = dummyBlock;
        actualGrid[4][21] = dummyBlock;
        actualGrid[6][19] = dummyBlock;
        doRotationr180r90();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 18);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 17);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 17);
        assertEqualBothGrids();
    }

    @Override
    @Test
    public void WallKickTest5r180r90() {
        moveBlockToStartPositionr180r90();
        actualGrid[4][22] = dummyBlock;
        actualGrid[4][21] = dummyBlock;
        actualGrid[5][19] = dummyBlock;
        actualGrid[6][19] = dummyBlock;
        doRotationr180r90();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 18);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 17);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 17);
        assertEqualBothGrids();
    }

    @Override
    @Test
    public void WallKickCollisionr180r90() {
        moveBlockToStartPositionr180r90();
        actualGrid[4][22] = dummyBlock;
        actualGrid[4][21] = dummyBlock;
        actualGrid[4][18] = dummyBlock;
        actualGrid[5][19] = dummyBlock;
        actualGrid[6][19] = dummyBlock;
        doRotationr180r90();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 20 );
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 19);
        assertEqualBothGrids();
    }

    @Override
    protected void moveBlockToStartPositionr180r270() {
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.RIGHT));
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.RIGHT));
    }

    @Override
    @Test
    public void WallKickTest1r180r270() {
        moveBlockToStartPositionr180r270();
        doRotationr180r270();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 20 );
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 19);
        assertEqualBothGrids();
    }

    @Override
    @Test
    public void WallKickTest2r180r270() {
        moveBlockToStartPositionr180r270();
        actualGrid[4][21] = dummyBlock;
        doRotationr180r270();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 20 );
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 19);
        assertEqualBothGrids();
    }

    @Override
    @Test
    public void WallKickTest3r180r270() {
        moveBlockToStartPositionr180r270();
        actualGrid[4][21] = dummyBlock;
        actualGrid[5][21] = dummyBlock;
        doRotationr180r270();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 22);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 22);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 21 );
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 20);
        assertEqualBothGrids();
    }

    @Override
    @Test
    public void WallKickTest4r180r270() {
        moveBlockToStartPositionr180r270();
        actualGrid[4][21] = dummyBlock;
        actualGrid[5][21] = dummyBlock;
        actualGrid[5][22] = dummyBlock;
        doRotationr180r270();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 18);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 17);
        assertEqualBothGrids();
    }

    @Override
    @Test
    public void WallKickTest5r180r270() {
        moveBlockToStartPositionr180r270();
        actualGrid[4][21] = dummyBlock;
        actualGrid[5][21] = dummyBlock;
        actualGrid[5][22] = dummyBlock;
        actualGrid[5][18] = dummyBlock;
        doRotationr180r270();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 18);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 17);
        assertEqualBothGrids();
    }

    @Override
    @Test
    public void WallKickCollisionr180r270() {
        moveBlockToStartPositionr180r270();
        actualGrid[4][21] = dummyBlock;
        actualGrid[5][21] = dummyBlock;
        actualGrid[5][22] = dummyBlock;
        actualGrid[5][18] = dummyBlock;
        actualGrid[5][19] = dummyBlock;
        doRotationr180r270();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 19);
        assertEqualBothGrids();
    }

    @Override
    protected void moveBlockToStartPositionr270r180() {
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.RIGHT));
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.RIGHT));
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.RIGHT));
    }

    @Override
    @Test
    public void WallKickTest1r270r1800() {
        moveBlockToStartPositionr270r180();
        doRotationr270r180();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 19);
        assertEqualBothGrids();
    }

    @Override
    @Test
    public void WallKickTest2r270r180() {
        moveBlockToStartPositionr270r180();
        actualGrid[6][20] = dummyBlock;
        doRotationr270r180();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 3, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 3, 19);
        assertEqualBothGrids();
    }

    @Override
    @Test
    public void WallKickTest3r270r180() {
        moveBlockToStartPositionr270r180();
        actualGrid[4][20] = dummyBlock;
        actualGrid[6][20] = dummyBlock;
        doRotationr270r180();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 3, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 3, 18);
        assertEqualBothGrids();
    }

    @Override
    @Test
    public void WallKickTest4r270r180() {
        moveBlockToStartPositionr270r180();
        actualGrid[4][20] = dummyBlock;
        actualGrid[6][20] = dummyBlock;
        actualGrid[3][18] = dummyBlock;
        doRotationr270r180();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 22);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 22);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 22);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 21);
        assertEqualBothGrids();
    }

    @Override
    @Test
    public void WallKickTest5r270r180() {
        moveBlockToStartPositionr270r180();
        actualGrid[4][20] = dummyBlock;
        actualGrid[6][20] = dummyBlock;
        actualGrid[6][22] = dummyBlock;
        actualGrid[3][18] = dummyBlock;
        doRotationr270r180();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 3, 22);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 22);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 22);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 3, 21);
        assertEqualBothGrids();
    }

    @Override
    @Test
    public void WallKickCollisionr270r180() {
        moveBlockToStartPositionr270r180();
        actualGrid[4][20] = dummyBlock;
        actualGrid[6][20] = dummyBlock;
        actualGrid[6][22] = dummyBlock;
        actualGrid[5][22] = dummyBlock;
        actualGrid[3][18] = dummyBlock;
        doRotationr270r180();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 19);
        assertEqualBothGrids();
    }

    @Override
    protected void moveBlockToStartPositionr270r0() {
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.RIGHT));
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.RIGHT));
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.RIGHT));
    }

    @Override
    @Test
    public void WallKickTest1r270r0() {
         moveBlockToStartPositionr270r0();
        doRotationr270r0();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 21);
        assertEqualBothGrids();
    }

    @Override
    @Test
    public void WallKickTest2r270r0() {
         moveBlockToStartPositionr270r0();
        actualGrid[6][21] = dummyBlock;
        doRotationr270r0();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 3, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 21);
        assertEqualBothGrids();
    }

    @Override
    @Test
    public void WallKickTest3r270r0() {
         moveBlockToStartPositionr270r0();
        actualGrid[6][21] = dummyBlock;
        actualGrid[3][20] = dummyBlock;
        doRotationr270r0();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 3, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 20);
        assertEqualBothGrids();
    }

    @Override
    @Test
    public void WallKickTest4r270r0() {
         moveBlockToStartPositionr270r0();
        actualGrid[6][21] = dummyBlock;
        actualGrid[3][20] = dummyBlock;
        actualGrid[4][19] = dummyBlock;
        doRotationr270r0();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 22);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 22);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 22);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 23);
        assertEqualBothGrids();
    }

    @Override
    @Test
    public void WallKickTest5r270r0() {
         moveBlockToStartPositionr270r0();
        actualGrid[6][21] = dummyBlock;
        actualGrid[6][22] = dummyBlock;
        actualGrid[3][20] = dummyBlock;
        actualGrid[4][19] = dummyBlock;
        doRotationr270r0();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 3, 22);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 22);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 22);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 23);
        assertEqualBothGrids();
    }

    @Override
    @Test
    public void WallKickCollisionr270r0() {
         moveBlockToStartPositionr270r0();
        actualGrid[6][21] = dummyBlock;
        actualGrid[6][22] = dummyBlock;
        actualGrid[5][22] = dummyBlock;
        actualGrid[3][20] = dummyBlock;
        actualGrid[4][19] = dummyBlock;
        doRotationr270r0();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 19);
        assertEqualBothGrids();
    }

    @Override
    protected void moveBlockToStartPositionr0r270() {
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
    }

    @Override
    @Test
    public void WallKickTest1r0r270() {
         moveBlockToStartPositionr0r270();
        doRotationr0r270();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 19);
        assertEqualBothGrids();
    }

    @Override
    @Test
    public void WallKickTest2r0r270() {
         moveBlockToStartPositionr0r270();
        actualGrid[4][21] = dummyBlock;
        doRotationr0r270();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 19);
        assertEqualBothGrids();
    }

    @Override
    @Test
    public void WallKickTest3r0r270() {
         moveBlockToStartPositionr0r270();
        actualGrid[4][21] = dummyBlock;
        actualGrid[5][21] = dummyBlock;
        doRotationr0r270();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 22);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 22);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 20);
        assertEqualBothGrids();
    }

    @Override
    @Test
    public void WallKickTest4r0r270() {
         moveBlockToStartPositionr0r270();
        actualGrid[4][21] = dummyBlock;
        actualGrid[5][21] = dummyBlock;
        actualGrid[5][22] = dummyBlock;
        doRotationr0r270();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 18);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 17);
        assertEqualBothGrids();
    }

    @Override
    @Test
    public void WallKickTest5r0r270() {
         moveBlockToStartPositionr0r270();
         actualGrid[4][19] = dummyBlock;
        actualGrid[4][21] = dummyBlock;
        actualGrid[5][21] = dummyBlock;
        actualGrid[5][22] = dummyBlock;
        doRotationr0r270();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 18);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 17);
        assertEqualBothGrids();
    }

    @Override
    @Test
    public void WallKickCollisionr0r270() {
         moveBlockToStartPositionr0r270();
         actualGrid[4][19] = dummyBlock;
        actualGrid[4][21] = dummyBlock;
        actualGrid[5][19] = dummyBlock;
        actualGrid[5][21] = dummyBlock;
        actualGrid[5][22] = dummyBlock;
        doRotationr0r270();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 21);
        assertEqualBothGrids();
    }
    
}
