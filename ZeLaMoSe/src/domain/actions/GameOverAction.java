/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.actions;

/**
 *
 * @author Patrick Zenhäusern
 */
public class GameOverAction extends Action {

    public GameOverAction(Long timestamp) {
        super(ActionType.GAMEOVER,timestamp);
    }
    
}
