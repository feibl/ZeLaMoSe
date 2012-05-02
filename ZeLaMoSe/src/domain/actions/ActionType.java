/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.actions;

/**
 *
 * @author Cyrill Lam <clam@hsr.ch>
 * 
 * Player relevant (input):
 * - rotation (direction)
 * - move (direction)
 * - harddrop
 * 
 * Simulation/UI Relevant (output):
 * - rotation (direction): rotate current block in direction by 90
 * - move (direction, speed): move block in direction (left, right, down) with speed (number of grids) 
 * - rmline (number of lines, offset): remove a number of lines, first with offset from bottom
 * - garbageline (line definition): add new line to bottom.
 * - newBlock: A new block enters the game
 * - GameOver: :-(
 * 
 */
public enum ActionType {
    ROTATION, MOVE, HARDDROP,
    REMOVELINE, GARBAGELINE, NEWBLOCK, GAMEOVER,MIRROR, CLEAR
}
