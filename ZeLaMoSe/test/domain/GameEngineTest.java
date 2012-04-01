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

/**
 *
 * @author Patrick Zenhäusern
 */
public class GameEngineTest {

    private GameEngine gameEngine;
    private FakeBlockQueue queue;

    @Before
    public void setUp() {
        queue = new FakeBlockQueue();
        fillInFakes();
        gameEngine = new GameEngine(12345,1,queue);

    }

    @After
    public void tearDown() {
        //gameEngine.print();
    }

    @Test
    public void checkInitialGrid() {
        for (int i = 0; i < gameEngine.gridheight; i++) {
            for (int j = 0; j < gameEngine.gridwidth; j++) {
                // assertEquals(null, (gameEngine.getGrid())[j][i]);
            }
        }
    }

    @Test
    public void addDownAction() {
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
    }

    @Test
    public void hardDropAction() {
        gameEngine.simulateAction(new HarddropAction(System.nanoTime()));
    }

    @Test
    public void moveLeft() {
        gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.LEFT, 1));
    }

    @Test
    public void rotate() {
        gameEngine.print();
        gameEngine.simulateAction(new RotateAction(System.nanoTime(), RotateAction.Direction.LEFT));
        gameEngine.print();
        gameEngine.simulateAction(new RotateAction(System.nanoTime(), RotateAction.Direction.LEFT));
        gameEngine.print();
        gameEngine.simulateAction(new RotateAction(System.nanoTime(), RotateAction.Direction.LEFT));
        gameEngine.print();
        gameEngine.simulateAction(new RotateAction(System.nanoTime(), RotateAction.Direction.LEFT));
        gameEngine.print();
    }

    
    private void fillInFakes() {
        queue.blocklist.add(new BlockI());
        queue.blocklist.add(new BlockJ());
        queue.blocklist.add(new BlockO());
        queue.blocklist.add(new BlockZ());
        queue.blocklist.add(new BlockS());
        queue.blocklist.add(new BlockL());
        queue.blocklist.add(new BlockI());
        queue.blocklist.add(new BlockI());
        queue.blocklist.add(new BlockI());
        queue.blocklist.add(new BlockI());
        
    }
}
