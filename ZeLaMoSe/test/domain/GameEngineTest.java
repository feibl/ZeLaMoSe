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
import org.junit.Before;
import org.junit.Test;
import org.junit.*;
import org.junit.rules.TestName;
import static org.junit.Assert.*;

/**
 *
 * @author Patrick Zenhäusern
 */
public class GameEngineTest {

    private GameEngine gameEngine;
    private FakeBlockQueue fakeQueue;
    private Block[][] testGrid;

    private void addCurrentBlockToTestGrid(int x, int y) {
        testGrid[x][y] = gameEngine.getCurrentBlock();
    }

    private void checkTestGrid() {
        for (int i = 0; i < gameEngine.gridheight; i++) {
            for (int j = 0; j < gameEngine.gridwidth; j++) {
                assertEquals(testGrid[j][i], (gameEngine.getGrid())[j][i]);
            }
        }
    }

    private void initializeGrid() {
        testGrid = new Block[Config.gridWidth][Config.gridHeight];
    }
    
    private void fillInFakes() {
        //Do not change the reihenfolge
        fakeQueue.blocklist.add(new IBlock());
        fakeQueue.blocklist.add(new JBlock());
        fakeQueue.blocklist.add(new OBlock());
        fakeQueue.blocklist.add(new LBlock());
        fakeQueue.blocklist.add(new JBlock());
        fakeQueue.blocklist.add(new OBlock());
        fakeQueue.blocklist.add(new OBlock());
        fakeQueue.blocklist.add(new OBlock());
        fakeQueue.blocklist.add(new ZBlock());
        fakeQueue.blocklist.add(new ZBlock());
        fakeQueue.blocklist.add(new ZBlock());
        fakeQueue.blocklist.add(new ZBlock());
        fakeQueue.blocklist.add(new ZBlock());
        fakeQueue.blocklist.add(new ZBlock());
        fakeQueue.blocklist.add(new ZBlock());
        fakeQueue.blocklist.add(new ZBlock());
    }
    @Rule
    public TestName testName = new TestName();

    @Before
    public void setUp() {
        fakeQueue = new FakeBlockQueue();
        initializeGrid();
        fillInFakes();
        gameEngine = new GameEngine(12345, 1, fakeQueue);
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
        addCurrentBlockToTestGrid(4, 22);
        addCurrentBlockToTestGrid(5, 22);
        addCurrentBlockToTestGrid(6, 22);
        addCurrentBlockToTestGrid(7, 22);
        checkTestGrid();
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
        addCurrentBlockToTestGrid(4, 19);
        addCurrentBlockToTestGrid(5, 19);
        addCurrentBlockToTestGrid(6, 19);
        addCurrentBlockToTestGrid(7, 19);

        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        checkTestGrid();
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
        addCurrentBlockToTestGrid(4, 0);
        addCurrentBlockToTestGrid(5, 0);
        addCurrentBlockToTestGrid(6, 0);
        addCurrentBlockToTestGrid(7, 0);

        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        addCurrentBlockToTestGrid(4, 23);
        addCurrentBlockToTestGrid(4, 22);
        addCurrentBlockToTestGrid(5, 22);
        addCurrentBlockToTestGrid(6, 22);

        checkTestGrid();
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
        addCurrentBlockToTestGrid(3, 22);    
        addCurrentBlockToTestGrid(4, 22);
        addCurrentBlockToTestGrid(5, 22);
        addCurrentBlockToTestGrid(6, 22);
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        checkTestGrid();
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
        addCurrentBlockToTestGrid(5, 23);
        addCurrentBlockToTestGrid(5, 22);
        addCurrentBlockToTestGrid(5, 21);
        addCurrentBlockToTestGrid(5, 20);

        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.LEFT));
        gameEngine.print();
        checkTestGrid();
        initializeGrid();
        
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
        addCurrentBlockToTestGrid(4, 21);
        addCurrentBlockToTestGrid(5, 21);
        addCurrentBlockToTestGrid(6, 21);
        addCurrentBlockToTestGrid(7, 21);
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.LEFT));
        gameEngine.print();
        checkTestGrid();
        initializeGrid();
        
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
        addCurrentBlockToTestGrid(6, 23);
        addCurrentBlockToTestGrid(6, 22);
        addCurrentBlockToTestGrid(6, 21);
        addCurrentBlockToTestGrid(6, 20);
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.LEFT));
        gameEngine.print();
        checkTestGrid();
        initializeGrid();
        
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
        addCurrentBlockToTestGrid(4, 22);
        addCurrentBlockToTestGrid(5, 22);
        addCurrentBlockToTestGrid(6, 22);
        addCurrentBlockToTestGrid(7, 22);
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.LEFT));
        checkTestGrid();
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
        addCurrentBlockToTestGrid(4, 0);
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        gameEngine.print();
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        addCurrentBlockToTestGrid(7, 0);
        addCurrentBlockToTestGrid(8, 0);
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        gameEngine.print();
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        addCurrentBlockToTestGrid(11, 0);
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        gameEngine.print();
        addCurrentBlockToTestGrid(4, 23);
        addCurrentBlockToTestGrid(4, 22);
        addCurrentBlockToTestGrid(5, 22);
        addCurrentBlockToTestGrid(6, 22);
        checkTestGrid();
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
        addCurrentBlockToTestGrid(11, 1);
        addCurrentBlockToTestGrid(11, 0);
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
        addCurrentBlockToTestGrid(1, 0);
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
        addCurrentBlockToTestGrid(3, 0);
        addCurrentBlockToTestGrid(4, 0);
        gameEngine.handleAction(new RotateAction(System.nanoTime(), RotateAction.Direction.LEFT));
        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.handleAction(new HardDropAction(System.nanoTime()));
        gameEngine.print();

        gameEngine.handleAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        addCurrentBlockToTestGrid(0, 1);
        addCurrentBlockToTestGrid(1, 1);
        addCurrentBlockToTestGrid(0, 0);
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
        
        addCurrentBlockToTestGrid(4, 23);
        addCurrentBlockToTestGrid(5, 23);
        addCurrentBlockToTestGrid(5, 22);
        addCurrentBlockToTestGrid(6, 22);
        checkTestGrid();
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
