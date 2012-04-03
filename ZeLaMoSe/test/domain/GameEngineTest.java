/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.actions.HarddropAction;
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
    private FakeBlockQueue queue;
    private Block[][] testGrid;

    private void addCurrentBlockToTestGrid(int x, int y) {
        testGrid[x][y] = gameEngine.getCurrentBlock();
    }

    private void checkGrid() {
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
        queue.blocklist.add(new BlockI());
        queue.blocklist.add(new BlockJ());
        queue.blocklist.add(new BlockO());
        queue.blocklist.add(new BlockL());
        queue.blocklist.add(new BlockJ());
        queue.blocklist.add(new BlockO());
        queue.blocklist.add(new BlockO());
        queue.blocklist.add(new BlockO());
        queue.blocklist.add(new BlockZ());
        queue.blocklist.add(new BlockZ());
        queue.blocklist.add(new BlockZ());
        queue.blocklist.add(new BlockZ());
        queue.blocklist.add(new BlockZ());
        queue.blocklist.add(new BlockZ());
        queue.blocklist.add(new BlockZ());
        queue.blocklist.add(new BlockZ());
    }
    @Rule
    public TestName name = new TestName();

    @Before
    public void setUp() {
        queue = new FakeBlockQueue();
        initializeGrid();
        fillInFakes();
        gameEngine = new GameEngine(12345, 1, queue);
        gameEngine.start();
        System.out.println("Start " + name.getMethodName() + "----------------------------------");
    }

    @After
    public void tearDown() {
        gameEngine.print();
        System.out.println("End " + name.getMethodName() + "----------------------------------");
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
        addCurrentBlockToTestGrid(4, 1);
        addCurrentBlockToTestGrid(5, 1);
        addCurrentBlockToTestGrid(6, 1);
        addCurrentBlockToTestGrid(7, 1);
        checkGrid();
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
        addCurrentBlockToTestGrid(4, 4);
        addCurrentBlockToTestGrid(5, 4);
        addCurrentBlockToTestGrid(6, 4);
        addCurrentBlockToTestGrid(7, 4);

        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        checkGrid();
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
        addCurrentBlockToTestGrid(4, 23);
        addCurrentBlockToTestGrid(5, 23);
        addCurrentBlockToTestGrid(6, 23);
        addCurrentBlockToTestGrid(7, 23);

        gameEngine.simulateAction(new HarddropAction(System.nanoTime()));
        checkGrid();
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
        addCurrentBlockToTestGrid(3, 1);    
        addCurrentBlockToTestGrid(4, 1);
        addCurrentBlockToTestGrid(5, 1);
        addCurrentBlockToTestGrid(6, 1);
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        checkGrid();
    }

    @Test
    public void rotateLeftAction() {
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
        addCurrentBlockToTestGrid(5, 0);
        addCurrentBlockToTestGrid(5, 1);
        addCurrentBlockToTestGrid(5, 2);
        addCurrentBlockToTestGrid(5, 3);

        gameEngine.simulateAction(new RotateAction(System.nanoTime(), RotateAction.Direction.LEFT));
        gameEngine.print();
        checkGrid();
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
        addCurrentBlockToTestGrid(4, 2);
        addCurrentBlockToTestGrid(5, 2);
        addCurrentBlockToTestGrid(6, 2);
        addCurrentBlockToTestGrid(7, 2);
        gameEngine.simulateAction(new RotateAction(System.nanoTime(), RotateAction.Direction.LEFT));
        gameEngine.print();
        checkGrid();
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
        addCurrentBlockToTestGrid(6, 0);
        addCurrentBlockToTestGrid(6, 1);
        addCurrentBlockToTestGrid(6, 2);
        addCurrentBlockToTestGrid(6, 3);
        gameEngine.simulateAction(new RotateAction(System.nanoTime(), RotateAction.Direction.LEFT));
        gameEngine.print();
        checkGrid();
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
        addCurrentBlockToTestGrid(4, 1);
        addCurrentBlockToTestGrid(5, 1);
        addCurrentBlockToTestGrid(6, 1);
        addCurrentBlockToTestGrid(7, 1);
        gameEngine.simulateAction(new RotateAction(System.nanoTime(), RotateAction.Direction.LEFT));
        checkGrid();
    }

    @Test
    public void rmLineAction() {
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
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.simulateAction(new HarddropAction(System.nanoTime()));
        gameEngine.print();
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        addCurrentBlockToTestGrid(4, 23);
        gameEngine.simulateAction(new HarddropAction(System.nanoTime()));
        gameEngine.print();
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        addCurrentBlockToTestGrid(7, 23);
        addCurrentBlockToTestGrid(8, 23);
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.simulateAction(new HarddropAction(System.nanoTime()));
        gameEngine.print();
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        addCurrentBlockToTestGrid(11, 23);
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.simulateAction(new HarddropAction(System.nanoTime()));
        gameEngine.print();
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        addCurrentBlockToTestGrid(4, 0);
        addCurrentBlockToTestGrid(4, 1);
        addCurrentBlockToTestGrid(5, 1);
        addCurrentBlockToTestGrid(6, 1);
        checkGrid();
    }

    @Test
    public void rmMultiLineAction() {
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
        addCurrentBlockToTestGrid(11, 22);
        addCurrentBlockToTestGrid(11, 23);
        gameEngine.simulateAction(new RotateAction(System.nanoTime(), RotateAction.Direction.LEFT));
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.simulateAction(new HarddropAction(System.nanoTime()));
        gameEngine.print();

        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        addCurrentBlockToTestGrid(1, 23);
        gameEngine.simulateAction(new RotateAction(System.nanoTime(), RotateAction.Direction.LEFT));
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.simulateAction(new HarddropAction(System.nanoTime()));
        gameEngine.print();

        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.simulateAction(new HarddropAction(System.nanoTime()));
        gameEngine.print();

        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        addCurrentBlockToTestGrid(3, 23);
        addCurrentBlockToTestGrid(4, 23);
        gameEngine.simulateAction(new RotateAction(System.nanoTime(), RotateAction.Direction.LEFT));
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.simulateAction(new HarddropAction(System.nanoTime()));
        gameEngine.print();

        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        addCurrentBlockToTestGrid(0, 22);
        addCurrentBlockToTestGrid(1, 22);
        addCurrentBlockToTestGrid(0, 23);
        gameEngine.simulateAction(new RotateAction(System.nanoTime(), RotateAction.Direction.RIGHT));
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
        gameEngine.simulateAction(new HarddropAction(System.nanoTime()));
        gameEngine.print();

        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.simulateAction(new HarddropAction(System.nanoTime()));
        gameEngine.print();

        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.simulateAction(new HarddropAction(System.nanoTime()));
        gameEngine.print();

        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.RIGHT, 1));
        gameEngine.simulateAction(new HarddropAction(System.nanoTime()));
        gameEngine.print();
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        addCurrentBlockToTestGrid(4, 0);
        addCurrentBlockToTestGrid(5, 0);
        addCurrentBlockToTestGrid(5, 1);
        addCurrentBlockToTestGrid(6, 1);
        checkGrid();
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
        gameEngine.simulateAction(new HarddropAction(System.nanoTime()));
        gameEngine.print();
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.simulateAction(new HarddropAction(System.nanoTime()));
        gameEngine.print();
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.simulateAction(new HarddropAction(System.nanoTime()));
        gameEngine.print();
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.simulateAction(new HarddropAction(System.nanoTime()));
        gameEngine.print();
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.simulateAction(new HarddropAction(System.nanoTime()));
        gameEngine.print();
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.simulateAction(new HarddropAction(System.nanoTime()));
        gameEngine.print();
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.simulateAction(new HarddropAction(System.nanoTime()));
        gameEngine.print();
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.simulateAction(new HarddropAction(System.nanoTime()));
        gameEngine.print();
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.simulateAction(new HarddropAction(System.nanoTime()));
        gameEngine.print();
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.simulateAction(new HarddropAction(System.nanoTime()));
        gameEngine.print();
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.simulateAction(new HarddropAction(System.nanoTime()));
        gameEngine.print();
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.simulateAction(new HarddropAction(System.nanoTime()));
        gameEngine.print();
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.simulateAction(new HarddropAction(System.nanoTime()));
        gameEngine.print();
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.simulateAction(new HarddropAction(System.nanoTime()));
        assertEquals(true, gameEngine.isGameOver());
    }
}
