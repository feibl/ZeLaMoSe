/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

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
        for (int i = 0; i < gameEngine.getGrid()[0].length; i++) {
            for (int j = 0; j < gameEngine.getGrid().length; j++) {
                assertEquals(expectedGrid[j][i], (gameEngine.getGrid())[j][i]);
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
        gameEngine.print();
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
        gameEngine.print();
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
        gameEngine.print();
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
        gameEngine.print();
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
        gameEngine.print();
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
        gameEngine.print();
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        addCurrentBlockToExpectedGrid(4, 0);
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        gameEngine.print();
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        addCurrentBlockToExpectedGrid(7, 0);
        addCurrentBlockToExpectedGrid(8, 0);
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        gameEngine.print();
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        addCurrentBlockToExpectedGrid(11, 0);
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        gameEngine.print();
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
        gameEngine.print();

        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        addCurrentBlockToExpectedGrid(1, 0);
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.LEFT));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        gameEngine.print();

        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        gameEngine.print();

        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        addCurrentBlockToExpectedGrid(3, 0);
        addCurrentBlockToExpectedGrid(4, 0);
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.LEFT));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        gameEngine.print();

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
        gameEngine.print();

        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        gameEngine.print();

        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        gameEngine.print();

        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        gameEngine.print();
        
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
        gameEngine.print();
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        gameEngine.print();
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        gameEngine.print();
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        gameEngine.print();
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        gameEngine.print();
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        gameEngine.print();
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        gameEngine.print();
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        gameEngine.print();
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        gameEngine.print();
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        gameEngine.print();
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        gameEngine.print();
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        gameEngine.print();
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        gameEngine.print();
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        assertEquals(true, gameEngine.isGameOver());
    }
}
