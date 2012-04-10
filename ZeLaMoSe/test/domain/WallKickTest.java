/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.Fake.FakeBlockQueue;
import domain.actions.RotateAction;
import domain.block.Block;
import domain.block.OBlock;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Patrick Zenhäusern
 */
 @Ignore
public abstract class WallKickTest {
    /*
     * To change this template, choose Tools | Templates and open the template in the editor.
     *
     *
     * /**
     *
     * @author Patrick Zenhäusern
     */

    protected GameEngine gameEngine;
    protected FakeBlockQueue fakeQueue;
    protected Block[][] expectedGrid;
    protected Block[][] actualGrid;
    protected OBlock dummyBlock;

    protected void addBlockToExpectedGrid(Block block, int x, int y) {
        expectedGrid[x][y] = block;
    }

    protected void assertEqualBothGrids() {
        for (int i = 0; i < gameEngine.gridheight; i++) {
            for (int j = 0; j < gameEngine.gridwidth; j++) {
                assertEquals(expectedGrid[j][i], (gameEngine.getGrid())[j][i]);
            }
        }
    }

    protected void cloneGridOnlyOBlocks() {
        for (int i = 0; i < gameEngine.gridheight; i++) {
            for (int j = 0; j < gameEngine.gridwidth; j++) {
                if (actualGrid[j][i] != null && actualGrid[j][i].equals(dummyBlock)) {
                    expectedGrid[j][i] = actualGrid[j][i];
                }

            }
        }
    }

    protected void initializeExpectedGrid() {
        expectedGrid = new Block[Config.gridWidth][Config.gridHeight];
    }

    protected abstract void fillInFakes();
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

    protected void doRotationr0r90() {
        cloneGridOnlyOBlocks();
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.RIGHT));
    }

    protected abstract void moveBlockToStartPositionr0r90();
    public abstract void WallKickTest1r0r90();
    public abstract void WallKickTest2r0r90();
    public abstract void WallKickTest3r0r90();
    public abstract void WallKickTest4r0r90();
    public abstract void WallKickTest5r0r90();
    public abstract void WallKickCollisionr0r90();

    protected void doRotationr90r0() {
        cloneGridOnlyOBlocks();
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.LEFT));
    }

    protected abstract void moveBlockToStartPositionr90r0();
    public abstract void WallKickTest1r90r0();
    public abstract void WallKickTest2r90r0();
    public abstract void WallKickTest3r90r0();
    public abstract void WallKickTest4r90r0();
    public abstract void WallKickTest5r90r0();
    public abstract void WallKickCollisionr90r0();

    protected void doRotationr90r180() {
        cloneGridOnlyOBlocks();
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.RIGHT));
    }

    protected abstract void moveBlockToStartPositionr90r180();
    public abstract void WallKickTest1r90r180();
    public abstract void WallKickTest2r90r180();
    public abstract void WallKickTest3r90r180();

    
    public abstract void WallKickTest4r90r180();

    
    public abstract void WallKickTest5r90r180();

    
    public abstract void WallKickCollisionr90r180();

    protected void doRotationr180r90() {
        cloneGridOnlyOBlocks();
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.LEFT));
    }

    protected abstract void moveBlockToStartPositionr180r90();

    
    public abstract void WallKickTest1r180r90();

    
    public abstract void WallKickTest2r180r90();

    
    public abstract void WallKickTest3r180r90();

    
    public abstract void WallKickTest4r180r90();

    
    public abstract void WallKickTest5r180r90();

    
    public abstract void WallKickCollisionr180r90();

    protected void doRotationr180r270() {
        cloneGridOnlyOBlocks();
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.RIGHT));
    }

    protected abstract void moveBlockToStartPositionr180r270();

    
    public abstract void WallKickTest1r180r270();

    
    public abstract void WallKickTest2r180r270();

    
    public abstract void WallKickTest3r180r270();

    
    public abstract void WallKickTest4r180r270();

    
    public abstract void WallKickTest5r180r270();

    
    public abstract void WallKickCollisionr180r270();

    protected void doRotationr270r180() {
        cloneGridOnlyOBlocks();
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.LEFT));
    }

    protected abstract void moveBlockToStartPositionr270r180();

    
    public abstract void WallKickTest1r270r1800();

    
    public abstract void WallKickTest2r270r180();

    
    public abstract void WallKickTest3r270r180();

    
    public abstract void WallKickTest4r270r180();

    
    public abstract void WallKickTest5r270r180();

    
    public abstract void WallKickCollisionr270r180();

    protected void doRotationr270r0() {
        cloneGridOnlyOBlocks();
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.RIGHT));
    }

    protected abstract void moveBlockToStartPositionr270r0();

    
    public abstract void WallKickTest1r270r0();

    
    public abstract void WallKickTest2r270r0();

    
    public abstract void WallKickTest3r270r0();

    
    public abstract void WallKickTest4r270r0();

    
    public abstract void WallKickTest5r270r0();

    
    public abstract void WallKickCollisionr270r0();

    protected void doRotationr0r270() {
        cloneGridOnlyOBlocks();
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.LEFT));
    }

    protected abstract void moveBlockToStartPositionr0r270();

    
    public abstract void WallKickTest1r0r270();

    
    public abstract void WallKickTest2r0r270();

    
    public abstract void WallKickTest3r0r270();

    
    public abstract void WallKickTest4r0r270();

    
    public abstract void WallKickTest5r0r270();

    
    public abstract void WallKickCollisionr0r270();
}
