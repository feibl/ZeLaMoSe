/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.actions.MoveAction;
import domain.actions.RotateAction;
import domain.block.IBlock;
import org.junit.Test;

/**
 *
 * @author Patrick Zenhäusern <pzenhaeu@hsr.ch>
 */
public class IWallKickTest extends WallKickTest {

    @Override
    protected void fillInFakes() {
        //Do not change the reihenfolge
        fakeQueue.blocklist.add(new IBlock(1,0));
        fakeQueue.blocklist.add(new IBlock(2,0));
    }

    @Override
    protected void moveBlockToStartPositionr0r90() {
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
    }

    @Test
    @Override
    public void WallKickTest1r0r90() {
        moveBlockToStartPositionr0r90();
        doRotationr0r90();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 10, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 10, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 10, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 10, 18);
        assertEqualBothGrids();
    }

    @Test
    @Override
    public void WallKickTest2r0r90() {
        moveBlockToStartPositionr0r90();
        actualStack[9][19] = dummyBlock;
        actualStack[10][19] = dummyBlock;
        doRotationr0r90();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 8, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 8, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 8, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 8, 18);
        assertEqualBothGrids();
    }

    @Test
    @Override
    public void WallKickTest3r0r90() {
        moveBlockToStartPositionr0r90();
        actualStack[8][19] = dummyBlock;
        actualStack[9][19] = dummyBlock;
        actualStack[10][19] = dummyBlock;
        doRotationr0r90();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 11, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 11, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 11, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 11, 18);
        assertEqualBothGrids();
    }

    @Test
    @Override
    public void WallKickTest4r0r90() {
        moveBlockToStartPositionr0r90();
        actualStack[8][21] = dummyBlock;
        actualStack[9][19] = dummyBlock;
        actualStack[10][19] = dummyBlock;
        actualStack[11][19] = dummyBlock;
        doRotationr0r90();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 8, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 8, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 8, 18);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 8, 17);
        assertEqualBothGrids();
    }

    @Test
    @Override
    public void WallKickTest5r0r90() {
        moveBlockToStartPositionr0r90();
        actualStack[8][19] = dummyBlock;
        actualStack[9][19] = dummyBlock;
        actualStack[10][19] = dummyBlock;
        actualStack[11][19] = dummyBlock;
        doRotationr0r90();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 11, 23);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 11, 22);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 11, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 11, 20);
        assertEqualBothGrids();
    }

    @Test
    @Override
    public void WallKickCollisionr0r90() {
        moveBlockToStartPositionr0r90();
        actualStack[8][19] = dummyBlock;
        actualStack[9][19] = dummyBlock;
        actualStack[10][19] = dummyBlock;
        actualStack[11][19] = dummyBlock;
        actualStack[11][21] = dummyBlock;
        doRotationr0r90();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 8, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 9, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 10, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 11, 20);
        assertEqualBothGrids();
    }

    @Override
    protected void moveBlockToStartPositionr90r0() {
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.RIGHT));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
    }

    @Test
    @Override
    public void WallKickTest1r90r0() {
        moveBlockToStartPositionr90r0();
        doRotationr90r0();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 20);
        assertEqualBothGrids();
    }

    @Test
    @Override
    public void WallKickTest2r90r0() {
        moveBlockToStartPositionr90r0();
        actualStack[4][20] = dummyBlock;
        doRotationr90r0();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 8, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 9, 20);
        assertEqualBothGrids();
    }

    @Test
    @Override
    public void WallKickTest3r90r0() {
        moveBlockToStartPositionr90r0();
        actualStack[7][20] = dummyBlock;
        doRotationr90r0();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 3, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 20);
        assertEqualBothGrids();

    }

    @Test
    @Override
    public void WallKickTest4r90r0() {
        moveBlockToStartPositionr90r0();
        actualStack[4][20] = dummyBlock;
        actualStack[9][20] = dummyBlock;
        doRotationr90r0();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 8, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 9, 21);
        assertEqualBothGrids();
    }

    @Test
    @Override
    public void WallKickTest5r90r0() {
        moveBlockToStartPositionr90r0();
        actualStack[4][20] = dummyBlock;
        actualStack[9][20] = dummyBlock;
        actualStack[9][21] = dummyBlock;
        doRotationr90r0();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 3, 18);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 18);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 18);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 18);
        assertEqualBothGrids();

    }

    @Test
    @Override
    public void WallKickCollisionr90r0() {
        moveBlockToStartPositionr90r0();
        actualStack[4][20] = dummyBlock;
        actualStack[4][18] = dummyBlock;
        actualStack[9][20] = dummyBlock;
        actualStack[9][21] = dummyBlock;
        doRotationr90r0();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 18);
        assertEqualBothGrids();
    }

    @Override
    protected void moveBlockToStartPositionr90r180() {
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.RIGHT));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
    }

    @Test
    @Override
    public void WallKickTest1r90r180() {
        moveBlockToStartPositionr90r180();
        doRotationr90r180();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 19);
        assertEqualBothGrids();
    }

    @Test
    @Override
    public void WallKickTest2r90r180() {
        moveBlockToStartPositionr90r180();
        actualStack[7][19] = dummyBlock;
        doRotationr90r180();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 3, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 19);
        assertEqualBothGrids();
    }

    @Test
    @Override
    public void WallKickTest3r90r180() {
        moveBlockToStartPositionr90r180();
        actualStack[5][19] = dummyBlock;
        doRotationr90r180();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 8, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 9, 19);

        assertEqualBothGrids();


    }

    @Test
    @Override
    public void WallKickTest4r90r180() {
        moveBlockToStartPositionr90r180();
        actualStack[5][19] = dummyBlock;
        actualStack[9][19] = dummyBlock;
        doRotationr90r180();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 3, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 21);
        assertEqualBothGrids();
    }

    @Test
    @Override
    public void WallKickTest5r90r180() {
        moveBlockToStartPositionr90r180();
        actualStack[5][19] = dummyBlock;
        actualStack[9][19] = dummyBlock;
        actualStack[5][21] = dummyBlock;
        doRotationr90r180();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 18);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 18);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 8, 18);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 9, 18);
        assertEqualBothGrids();

    }

    @Test
    @Override
    public void WallKickCollisionr90r180() {
        moveBlockToStartPositionr90r180();
        actualStack[5][19] = dummyBlock;
        actualStack[9][19] = dummyBlock;
        actualStack[5][21] = dummyBlock;
        actualStack[7][18] = dummyBlock;
        doRotationr90r180();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 18);
        assertEqualBothGrids();
    }

    @Override
    protected void moveBlockToStartPositionr180r90() {
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.RIGHT));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.RIGHT));
    }

    @Test
    @Override
    public void WallKickTest1r180r90() {
        doRotationr180r90();
        moveBlockToStartPositionr180r90();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 18);
        assertEqualBothGrids();
    }

    @Test
    @Override
    public void WallKickTest2r180r90() {
        moveBlockToStartPositionr180r90();
        actualStack[6][21] = dummyBlock;
        doRotationr180r90();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 18);
        assertEqualBothGrids();
    }

    @Test
    @Override
    public void WallKickTest3r180r90() {
        moveBlockToStartPositionr180r90();
        actualStack[6][21] = dummyBlock;
        actualStack[7][21] = dummyBlock;
        doRotationr180r90();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 18);
        assertEqualBothGrids();
    }

    @Test
    @Override
    public void WallKickTest4r180r90() {
        moveBlockToStartPositionr180r90();
        actualStack[4][21] = dummyBlock;
        actualStack[6][21] = dummyBlock;
        actualStack[7][21] = dummyBlock;
        doRotationr180r90();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 18);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 17);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 16);
        assertEqualBothGrids();
    }

    @Test
    @Override
    public void WallKickTest5r180r90() {
        moveBlockToStartPositionr180r90();
        actualStack[6][21] = dummyBlock;
        actualStack[7][21] = dummyBlock;
        actualStack[4][18] = dummyBlock;
        actualStack[7][18] = dummyBlock;
        doRotationr180r90();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 22);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 19);
        assertEqualBothGrids();
    }

    @Test
    @Override
    public void WallKickCollisionr180r90() {
        moveBlockToStartPositionr180r90();
        actualStack[4][21] = dummyBlock;
        actualStack[6][21] = dummyBlock;
        actualStack[7][21] = dummyBlock;
        actualStack[4][18] = dummyBlock;
        actualStack[7][18] = dummyBlock;
        doRotationr180r90();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 19);
        assertEqualBothGrids();
    }

    @Override
    protected void moveBlockToStartPositionr180r270() {
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.RIGHT));
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.RIGHT));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
    }

    @Test
    @Override
    public void WallKickTest1r180r270() {
        moveBlockToStartPositionr180r270();
        doRotationr180r270();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 18);
        assertEqualBothGrids();
    }

    @Test
    @Override
    public void WallKickTest2r180r270() {
        moveBlockToStartPositionr180r270();
        actualStack[5][20] = dummyBlock;
        doRotationr180r270();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 18);
        assertEqualBothGrids();
    }

    @Test
    @Override
    public void WallKickTest3r180r270() {
        moveBlockToStartPositionr180r270();
        actualStack[5][20] = dummyBlock;
        actualStack[7][18] = dummyBlock;
        doRotationr180r270();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 18);
        assertEqualBothGrids();
    }

    @Test
    @Override
    public void WallKickTest4r180r270() {
        moveBlockToStartPositionr180r270();
        actualStack[4][20] = dummyBlock;
        actualStack[5][20] = dummyBlock;
        actualStack[7][18] = dummyBlock;
        doRotationr180r270();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 22);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 19);
        assertEqualBothGrids();
    }

    @Test
    @Override
    public void WallKickTest5r180r270() {
        moveBlockToStartPositionr180r270();
        actualStack[4][20] = dummyBlock;
        actualStack[5][20] = dummyBlock;
        actualStack[7][18] = dummyBlock;
        actualStack[7][20] = dummyBlock;
        doRotationr180r270();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 18);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 17);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 16);
        assertEqualBothGrids();
    }

    @Test
    @Override
    public void WallKickCollisionr180r270() {
        moveBlockToStartPositionr180r270();
        actualStack[4][20] = dummyBlock;
        actualStack[4][18] = dummyBlock;
        actualStack[5][20] = dummyBlock;
        actualStack[7][18] = dummyBlock;
        actualStack[7][20] = dummyBlock;
        doRotationr180r270();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 19);
        assertEqualBothGrids();
    }

    @Override
    protected void moveBlockToStartPositionr270r180() {
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.RIGHT));
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.RIGHT));
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.RIGHT));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
    }

    @Test
    @Override
    public void WallKickTest1r270r1800() {
        moveBlockToStartPositionr270r180();
        doRotationr270r180();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 19);
        assertEqualBothGrids();
    }

    @Test
    @Override
    public void WallKickTest2r270r180() {
        moveBlockToStartPositionr270r180();
        actualStack[6][19] = dummyBlock;
        doRotationr270r180();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 2, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 3, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 19);
        assertEqualBothGrids();
    }

    @Test
    @Override
    public void WallKickTest3r270r180() {
        moveBlockToStartPositionr270r180();
        actualStack[4][19] = dummyBlock;
        doRotationr270r180();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 8, 19);
        assertEqualBothGrids();
    }

    @Test
    @Override
    public void WallKickTest4r270r180() {
        moveBlockToStartPositionr270r180();
        actualStack[4][19] = dummyBlock;
        actualStack[6][19] = dummyBlock;
        doRotationr270r180();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 2, 18);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 3, 18);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 18);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 18);
        assertEqualBothGrids();
    }

    @Test
    @Override
    public void WallKickTest5r270r180() {
        moveBlockToStartPositionr270r180();
        actualStack[4][19] = dummyBlock;
        actualStack[6][19] = dummyBlock;
        actualStack[4][18] = dummyBlock;
        doRotationr270r180();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 8, 21);
        assertEqualBothGrids();
    }

    @Test
    @Override
    public void WallKickCollisionr270r180() {
        moveBlockToStartPositionr270r180();
        actualStack[4][19] = dummyBlock;
        actualStack[6][19] = dummyBlock;
        actualStack[6][21] = dummyBlock;
        actualStack[4][18] = dummyBlock;
        doRotationr270r180();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 18);
        assertEqualBothGrids();
    }

    @Override
    protected void moveBlockToStartPositionr270r0() {
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.RIGHT));
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.RIGHT));
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.RIGHT));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
    }

    @Test
    @Override
    public void WallKickTest1r270r0() {
        moveBlockToStartPositionr270r0();
        doRotationr270r0();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 20);
        assertEqualBothGrids();
    }

    @Test
    @Override
    public void WallKickTest2r270r0() {
        moveBlockToStartPositionr270r0();
        actualStack[4][20] = dummyBlock;
        doRotationr270r0();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 8, 20);
        assertEqualBothGrids();
    }

    @Test
    @Override
    public void WallKickTest3r270r0() {
        moveBlockToStartPositionr270r0();
        actualStack[6][20] = dummyBlock;
        doRotationr270r0();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 2, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 3, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 20);
        assertEqualBothGrids();
    }

    @Test
    @Override
    public void WallKickTest4r270r0() {
        moveBlockToStartPositionr270r0();
        actualStack[6][20] = dummyBlock;
        actualStack[2][20] = dummyBlock;
        doRotationr270r0();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 18);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 18);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 18);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 8, 18);
        assertEqualBothGrids();
    }

    @Test
    @Override
    public void WallKickTest5r270r0() {
        moveBlockToStartPositionr270r0();
        actualStack[6][20] = dummyBlock;
        actualStack[6][18] = dummyBlock;
        actualStack[2][20] = dummyBlock;
        doRotationr270r0();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 2, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 3, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 21);
        assertEqualBothGrids();
    }

    @Test
    @Override
    public void WallKickCollisionr270r0() {
        moveBlockToStartPositionr270r0();
        actualStack[6][20] = dummyBlock;
        actualStack[6][18] = dummyBlock;
        actualStack[2][21] = dummyBlock;
        actualStack[2][20] = dummyBlock;
        doRotationr270r0();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 18);
        assertEqualBothGrids();
    }

    @Override
    protected void moveBlockToStartPositionr0r270() {
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.RIGHT));
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.RIGHT));
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.RIGHT));
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.RIGHT));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
    }

    @Test
    @Override
    public void WallKickTest1r0r270() {
        moveBlockToStartPositionr0r270();
        doRotationr0r270();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 18);
        assertEqualBothGrids();
    }

    @Test
    @Override
    public void WallKickTest2r0r270() {
        moveBlockToStartPositionr0r270();
        actualStack[5][21] = dummyBlock;
        doRotationr0r270();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 18);
        assertEqualBothGrids();
    }

    @Test
    @Override
    public void WallKickTest3r0r270() {
        moveBlockToStartPositionr0r270();
        actualStack[5][21] = dummyBlock;
        actualStack[4][18] = dummyBlock;
        doRotationr0r270();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 18);
        assertEqualBothGrids();
    }

    @Test
    @Override
    public void WallKickTest4r0r270() {
        moveBlockToStartPositionr0r270();
        actualStack[5][21] = dummyBlock;
        actualStack[4][18] = dummyBlock;
        actualStack[7][21] = dummyBlock;
        doRotationr0r270();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 23);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 22);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 20);
        assertEqualBothGrids();
    }

    @Test
    @Override
    public void WallKickTest5r0r270() {
        moveBlockToStartPositionr0r270();
        actualStack[5][21] = dummyBlock;
        actualStack[4][18] = dummyBlock;
        actualStack[4][21] = dummyBlock;
        actualStack[7][21] = dummyBlock;
        doRotationr0r270();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 18);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 17);
        assertEqualBothGrids();
    }

    @Test
    @Override
    public void WallKickCollisionr0r270() {
        moveBlockToStartPositionr0r270();
        actualStack[5][21] = dummyBlock;
        actualStack[4][18] = dummyBlock;
        actualStack[4][21] = dummyBlock;
        actualStack[7][21] = dummyBlock;
        actualStack[7][19] = dummyBlock;
        doRotationr0r270();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 4, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 5, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 6, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 7, 20);
        assertEqualBothGrids();
    }
}
