/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.actions.HarddropAction;
import org.junit.Before;
import org.junit.Test;
import org.junit.*;
import static org.junit.Assert.*;
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
    public void checkIfGridIsEmpty(){
       for(int i = 0; i < gameEngine.gridheight; i++){
           for(int j= 0; j < gameEngine.gridwidth; j++){
               assertEquals(null, (gameEngine.getGrid())[j][i]);
           }
       }
    }
    
    @Test 
    public void addDownAction(){
       gameEngine.simulateAction(new HarddropAction(1));
       assertEquals(false,true);
    }
    
}
