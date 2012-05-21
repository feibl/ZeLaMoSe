/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.actions.GarbageLineAction;
import domain.actions.HardDropAction;
import domain.actions.MoveAction;
import domain.actions.RotateAction;
import domain.block.*;
import domain.fake.FakeBlockQueue;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 *
 * @author Patrick Zenhäusern <pzenhaeu@hsr.ch>
 */
public class GameEngineTest {

    private GameEngine gameEngine;
    private FakeBlockQueue fakeQueue;
    private BlockAbstract[][] expectedGrid;

    private void addCurrentBlockToExpectedGrid(int x, int y) {
        expectedGrid[x][y] = gameEngine.getCurrentBlock();
    }

    private void assertEqualBothGrids() {
        BlockAbstract[][] grid = gameEngine.getGrid();
        for (int i = 0; i < grid[0].length; i++) {
            for (int j = 0; j < grid.length; j++) {
                assertEquals(expectedGrid[j][i], grid[j][i]);
            }
        }
    }

    private void initializeExpectedGrid() {
        expectedGrid = new BlockAbstract[Config.gridWidth][Config.gridHeight];
    }
    
    private void fillInFakes() {
        //Do not change the reihenfolge
        fakeQueue.blocklist.add(new IBlock(1,0));
        fakeQueue.blocklist.add(new JBlock(2,0));
        fakeQueue.blocklist.add(new OBlock(3,0));
        fakeQueue.blocklist.add(new LBlock(4,0));
        fakeQueue.blocklist.add(new JBlock(5,0));
        fakeQueue.blocklist.add(new OBlock(6,0));
        fakeQueue.blocklist.add(new OBlock(7,0));
        fakeQueue.blocklist.add(new OBlock(8,0));
        fakeQueue.blocklist.add(new ZBlock(9,0));
        fakeQueue.blocklist.add(new ZBlock(10,0));
        fakeQueue.blocklist.add(new ZBlock(11,0));
        fakeQueue.blocklist.add(new ZBlock(12,0));
        fakeQueue.blocklist.add(new ZBlock(13,0));
        fakeQueue.blocklist.add(new ZBlock(14,0));
        fakeQueue.blocklist.add(new ZBlock(15,0));
        fakeQueue.blocklist.add(new ZBlock(16,0));
    }
    
    @Rule
    public TestName testName = new TestName();

    @Before
    public void setUp() {
        fakeQueue = new FakeBlockQueue();
        initializeExpectedGrid();
        fillInFakes();
        gameEngine = new GameEngine(12345, 1, fakeQueue,0);
        gameEngine.startGame();
        System.out.println("Start " + testName.getMethodName() + "----------------------------------");
    }

    @After
    public void tearDown() {
        System.out.println(gameEngine);
        System.out.println("End " + testName.getMethodName() + "----------------------------------");
    }

    @Test
    public void newBlockAction() {
        /*Expected:
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][I][I][I][I][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
         */
        addCurrentBlockToExpectedGrid(4, 22);
        addCurrentBlockToExpectedGrid(5, 22);
        addCurrentBlockToExpectedGrid(6, 22);
        addCurrentBlockToExpectedGrid(7, 22);
        assertEqualBothGrids();
    }

    @Test
    public void downAction() {
        /*Expected:
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][I][I][I][I][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
         */
        addCurrentBlockToExpectedGrid(4, 19);
        addCurrentBlockToExpectedGrid(5, 19);
        addCurrentBlockToExpectedGrid(6, 19);
        addCurrentBlockToExpectedGrid(7, 19);

        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        assertEqualBothGrids();
    }

    @Test
    public void hardDropAction() {
        /*Expected:
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][I][I][I][I][ ][ ][ ][ ]
         */
        addCurrentBlockToExpectedGrid(4, 0);
        addCurrentBlockToExpectedGrid(5, 0);
        addCurrentBlockToExpectedGrid(6, 0);
        addCurrentBlockToExpectedGrid(7, 0);

        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        addCurrentBlockToExpectedGrid(4, 23);
        addCurrentBlockToExpectedGrid(4, 22);
        addCurrentBlockToExpectedGrid(5, 22);
        addCurrentBlockToExpectedGrid(6, 22);

        assertEqualBothGrids();
    }

    @Test
    public void leftMoveAction() {
        /*Expected:
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][I][I][I][I][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
         */
        addCurrentBlockToExpectedGrid(3, 22);    
        addCurrentBlockToExpectedGrid(4, 22);
        addCurrentBlockToExpectedGrid(5, 22);
        addCurrentBlockToExpectedGrid(6, 22);
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        assertEqualBothGrids();
    }

    @Test
    public void rotateLeftActionIBlockWallKickTest1() {
        System.out.println(gameEngine);
        //First Rotate Left
        /*Expected:
        [ ][ ][ ][ ][ ][I][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][I][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][I][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][I][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
         */
        addCurrentBlockToExpectedGrid(5, 23);
        addCurrentBlockToExpectedGrid(5, 22);
        addCurrentBlockToExpectedGrid(5, 21);
        addCurrentBlockToExpectedGrid(5, 20);

        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.LEFT));
        System.out.println(gameEngine);
        assertEqualBothGrids();
        initializeExpectedGrid();
        
        //Second Rotate Left
        /*Expected:
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][I][I][I][I][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
         */
        addCurrentBlockToExpectedGrid(4, 21);
        addCurrentBlockToExpectedGrid(5, 21);
        addCurrentBlockToExpectedGrid(6, 21);
        addCurrentBlockToExpectedGrid(7, 21);
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.LEFT));
        System.out.println(gameEngine);
        assertEqualBothGrids();
        initializeExpectedGrid();
        
        //Third Rotate Left
        /*Expected:
        [ ][ ][ ][ ][ ][ ][I][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][I][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][I][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][I][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
         */
        addCurrentBlockToExpectedGrid(6, 23);
        addCurrentBlockToExpectedGrid(6, 22);
        addCurrentBlockToExpectedGrid(6, 21);
        addCurrentBlockToExpectedGrid(6, 20);
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.LEFT));
        System.out.println(gameEngine);
        assertEqualBothGrids();
        initializeExpectedGrid();
        
        //Fourth Rotate Left
        /*Expected:
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][I][I][I][I][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
         */
        addCurrentBlockToExpectedGrid(4, 22);
        addCurrentBlockToExpectedGrid(5, 22);
        addCurrentBlockToExpectedGrid(6, 22);
        addCurrentBlockToExpectedGrid(7, 22);
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.LEFT));
        assertEqualBothGrids();
    }

    @Test
    public void removeLineAction() {
        /*Expected:
        [ ][ ][ ][ ][J][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][J][J][J][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][J][ ][ ][O][O][ ][ ][L]
         */
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        System.out.println(gameEngine);
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        addCurrentBlockToExpectedGrid(4, 0);
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        System.out.println(gameEngine);
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        addCurrentBlockToExpectedGrid(7, 0);
        addCurrentBlockToExpectedGrid(8, 0);
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        System.out.println(gameEngine);
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        addCurrentBlockToExpectedGrid(11, 0);
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        System.out.println(gameEngine);
        addCurrentBlockToExpectedGrid(4, 23);
        addCurrentBlockToExpectedGrid(4, 22);
        addCurrentBlockToExpectedGrid(5, 22);
        addCurrentBlockToExpectedGrid(6, 22);
        assertEqualBothGrids();
    }

    @Test
    public void removeMultiLineAction() {
        /*Expected:
         * multi lines remove: 2
        [ ][ ][ ][ ][Z][Z][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][Z][Z][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [J][J][ ][ ][ ][ ][ ][ ][ ][ ][ ][I]
        [J][J][ ][L][L][ ][ ][ ][ ][ ][ ][I]
         */
        addCurrentBlockToExpectedGrid(11, 1);
        addCurrentBlockToExpectedGrid(11, 0);
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.LEFT));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        System.out.println(gameEngine);

        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        addCurrentBlockToExpectedGrid(1, 0);
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.LEFT));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        System.out.println(gameEngine);

        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        System.out.println(gameEngine);

        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        addCurrentBlockToExpectedGrid(3, 0);
        addCurrentBlockToExpectedGrid(4, 0);
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.LEFT));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        System.out.println(gameEngine);

        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        addCurrentBlockToExpectedGrid(0, 1);
        addCurrentBlockToExpectedGrid(1, 1);
        addCurrentBlockToExpectedGrid(0, 0);
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.RIGHT));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        System.out.println(gameEngine);

        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        System.out.println(gameEngine);

        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        System.out.println(gameEngine);

        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        System.out.println(gameEngine);
        
        addCurrentBlockToExpectedGrid(4, 23);
        addCurrentBlockToExpectedGrid(5, 23);
        addCurrentBlockToExpectedGrid(5, 22);
        addCurrentBlockToExpectedGrid(6, 22);
        assertEqualBothGrids();
    }

    @Test
    public void gameOver() {
        /* Expected:
         * Game Over
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][Z][Z][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][Z][Z][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][Z][Z][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][Z][Z][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][Z][Z][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][Z][Z][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][Z][Z][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][Z][Z][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][Z][Z][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][Z][Z][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][O][O][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][O][O][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][O][O][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][O][O][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][O][O][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][J][O][O][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][J][J][J][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][L][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][L][L][L][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][O][O][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][J][O][O][ ][ ][ ][ ][ ]
        [Z][Z][ ][ ][J][J][J][ ][ ][ ][ ][ ]
        [ ][Z][Z][ ][I][I][I][I][ ][ ][ ][ ]
         */
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        System.out.println(gameEngine);
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        System.out.println(gameEngine);
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        System.out.println(gameEngine);
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        System.out.println(gameEngine);
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        System.out.println(gameEngine);
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        System.out.println(gameEngine);
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        System.out.println(gameEngine);
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        System.out.println(gameEngine);
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        System.out.println(gameEngine);
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        System.out.println(gameEngine);
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        System.out.println(gameEngine);
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        System.out.println(gameEngine);
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        System.out.println(gameEngine);
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        assertEquals(true, gameEngine.getGameOver());
    }
    
    @Test
    public void garbageLineCollision() {
        /*Expected:
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][I][I][I][I][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
         */
        addCurrentBlockToExpectedGrid(4, 3);
        addCurrentBlockToExpectedGrid(5, 3);
        addCurrentBlockToExpectedGrid(6, 3);
        addCurrentBlockToExpectedGrid(7, 3);
        

        for (int i = 0; i < 21; i++) {
            gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        }
        
        int numberOfLines = 3;
        int gridWidth = 12;
        BlockAbstract[][] garbageLines = new BlockAbstract[12][numberOfLines];
        
        int emptyXPosition = 3;
        GarbageBlock garbageBlock = new GarbageBlock(Integer.MAX_VALUE, 0);
        for (int x = 0; x < gridWidth; ++x) {
            if (x == emptyXPosition) {
                continue;
            }
            for (int y = 0; y < numberOfLines; ++y) {
                garbageLines[x][y] = garbageBlock;
            }
        }
        addGarbageLinesToExpectedGrid(garbageLines);
        
        gameEngine.handleAction(new GarbageLineAction(System.nanoTime(), garbageLines));
        System.out.println(gameEngine);
        assertEqualBothGrids();
    }
    
    @Test
    public void garbageLine2() {
        /*Expected:
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][I][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][I][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][I][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][I][ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ][ ]
         */
        addCurrentBlockToExpectedGrid(5, 4);
        addCurrentBlockToExpectedGrid(5, 3);
        addCurrentBlockToExpectedGrid(5, 2);
        addCurrentBlockToExpectedGrid(5, 1);
        

        for (int i = 0; i < 19; i++) {
            gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        }
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.LEFT));

        BlockAbstract[][] garbageLines = gameEngine.createGarbageLine(3, 5);
        addGarbageLinesToExpectedGrid(garbageLines);
        
        gameEngine.handleAction(new GarbageLineAction(System.nanoTime(), garbageLines));
        System.out.println(gameEngine);
        assertEqualBothGrids();
    }  

    private void addGarbageLinesToExpectedGrid(BlockAbstract[][] garbageLines) {
        for (int y = 0; y < garbageLines[0].length; y++) {
            for (int x = 0; x < Config.gridWidth; x++) {
                if (garbageLines[x][y] != null) {
                    expectedGrid[x][y] = garbageLines[x][y];
                }
            }
        }
    }
}
