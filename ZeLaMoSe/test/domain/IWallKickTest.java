/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.Fake.FakeBlockQueue;
import domain.actions.HardDropAction;
import domain.actions.MoveAction;
import domain.actions.RotateAction;
import domain.block.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Patrick Zenhäusern
 */
public class IWallKickTest {

    private GameEngine gameEngine;
    private FakeBlockQueue fakeQueue;
    private Block[][] expectedGrid;
    private Block[][] actualGrid;
    private OBlock dummyBlock;

    private void addBlockToExpectedGrid(Block block, int x, int y) {
        expectedGrid[x][y] = block;
    }

    private void assertEqualBothGrids() {
        for (int i = 0; i < gameEngine.gridheight; i++) {
            for (int j = 0; j < gameEngine.gridwidth; j++) {
                assertEquals(expectedGrid[j][i], (gameEngine.getGrid())[j][i]);
            }
        }
    }

    private void cloneGridOnlyOBlocks() {
        for (int i = 0; i < gameEngine.gridheight; i++) {
            for (int j = 0; j < gameEngine.gridwidth; j++) {
                if (actualGrid[j][i] != null && actualGrid[j][i].equals(dummyBlock)) {
                    expectedGrid[j][i] = actualGrid[j][i];
                }

            }
        }
    }

    private void initializeExpectedGrid() {
        expectedGrid = new Block[Config.gridWidth][Config.gridHeight];
    }

    private void fillInFakes() {
        //Do not change the reihenfolge
        fakeQueue.blocklist.add(new IBlock());
    }

    private void moveAndRotateIBlockr0r90() {
        cloneGridOnlyOBlocks();
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.RIGHT));
    }
    @Rule
    public TestName testName = new TestName();

    @Before
    public void setUp() {
        fakeQueue = new FakeBlockQueue();
        initializeExpectedGrid();
        fillInFakes();
        gameEngine = new GameEngine(12345, 1, fakeQueue);
        gameEngine.startGame();
        actualGrid = gameEngine.getGrid();
        dummyBlock = new OBlock();
        System.out.println("Start " + testName.getMethodName() + "----------------------------------");
    }

    @After
    public void tearDown() {
        gameEngine.print();
        System.out.println("End " + testName.getMethodName() + "----------------------------------");
    }

    @Test
    public void IWallKickTest1r0r90() {
        moveAndRotateIBlockr0r90();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 10, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 10, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 10, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 10, 18);
        assertEqualBothGrids();
    }

    @Test
    public void IWallKickTest2r0r90() {
        actualGrid[9][19] = dummyBlock;
        actualGrid[10][19] = dummyBlock;
        moveAndRotateIBlockr0r90();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 8, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 8, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 8, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 8, 18);
        assertEqualBothGrids();
    }

    @Test
    public void IWallKickTest3r0r90() {
        actualGrid[8][19] = dummyBlock;
        actualGrid[9][19] = dummyBlock;
        actualGrid[10][19] = dummyBlock;
        moveAndRotateIBlockr0r90();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 11, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 11, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 11, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 11, 18);
        assertEqualBothGrids();
    }

    @Test
    public void IWallKickTest4r0r90() {
        actualGrid[8][21] = dummyBlock;
        actualGrid[9][19] = dummyBlock;
        actualGrid[10][19] = dummyBlock;
        actualGrid[11][19] = dummyBlock;
        moveAndRotateIBlockr0r90();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 8, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 8, 19);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 8, 18);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 8, 17);
        assertEqualBothGrids();
    }

    @Test
    public void IWallKickTest5r0r90() {
        actualGrid[8][19] = dummyBlock;
        actualGrid[9][19] = dummyBlock;
        actualGrid[10][19] = dummyBlock;
        actualGrid[11][19] = dummyBlock;
        moveAndRotateIBlockr0r90();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 11, 23);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 11, 22);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 11, 21);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 11, 20);
        assertEqualBothGrids();
    }

    @Test
    public void IWallKickCollisionr0r90() {
        actualGrid[8][19] = dummyBlock;
        actualGrid[9][19] = dummyBlock;
        actualGrid[10][19] = dummyBlock;
        actualGrid[11][19] = dummyBlock;
        actualGrid[11][21] = dummyBlock;
        moveAndRotateIBlockr0r90();
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 8, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 9, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 10, 20);
        addBlockToExpectedGrid(gameEngine.getCurrentBlock(), 11, 20);
        assertEqualBothGrids();
    }
}
