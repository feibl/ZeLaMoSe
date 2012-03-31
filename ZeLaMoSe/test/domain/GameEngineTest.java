/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.actions.HarddropAction;
import domain.actions.MoveAction;
import org.junit.Before;
import org.junit.Test;
import org.junit.*;
/**
 *
 * @author Patrick Zenhäusern
 */
public class GameEngineTest {
    private GameEngine gameEngine;
    
    @Before
    public void setUp() {
        gameEngine =  new GameEngine(12345);
    }
    
    @After
    public void tearDown(){
           gameEngine.print();
    }
    
    @Test
    public void checkInitialGrid(){
       for(int i = 0; i < gameEngine.gridheight; i++){
           for(int j= 0; j < gameEngine.gridwidth; j++){
              // assertEquals(null, (gameEngine.getGrid())[j][i]);
           }
       }
    }
    
    @Test 
    public void addDownAction(){
       gameEngine.simulateAction(new MoveAction(System.nanoTime(), MoveAction.Direction.DOWN, 1));
    }
    
    @Test
    public void hardDropAction(){
       gameEngine.simulateAction(new HarddropAction(System.nanoTime()));
    }
    
}
