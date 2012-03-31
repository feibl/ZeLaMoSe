/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.actions.*;
import domain.block.Block;
import java.util.ArrayList;
import java.util.Observable;

/**
 *
 * @author Cyrill
 */
public class GameEngine extends Observable implements GameEngineInterface {

    private Action lastAction;
    private int sessionId;
    private Block[][] grid;
    int gridwidth = 12, gridheight = 24, defaultX = 4, defaultY = 23;
    private BlockQueue queue;
    private Block currentBlock;

    public GameEngine(int sessionId) {
        this(sessionId, System.nanoTime());
    }

    public GameEngine(int sessionId, long seed) {
        this.sessionId = sessionId;
        grid = new Block[gridwidth][gridheight];
        queue = new BlockQueue(seed);
    }

    public void setLastAction(Action action) {
        lastAction = action;
        setChanged();
        notifyObservers();
    }

    @Override
    public int sessionId() {
        return sessionId;
    }

    @Override
    public void simulateAction(Action action) {
        switch (action.getType()) {
            case MOVE:
                handleMoveAction((MoveAction) action);
                break;
            case ROTATION:
                handleRotateAction((RotateAction) action);
                break;
            case HARDDROP:
                handleHarddropAction();
                break;

        }
    }

    @Override
    public Action getSimulationState() {
        return lastAction;
    }

    private void handleMoveAction(MoveAction moveAction) {
        switch (moveAction.getDirection()) {
            case LEFT:
                moveSidewards(-1, moveAction);
                break;
            case RIGHT:
                moveSidewards(1, moveAction);
                break;
            case DOWN:
                moveDownwards(moveAction);
                break;

        }
    }

    private boolean checkForCollision() {
        boolean[][] blockGrid = currentBlock.getBlockGrid();
        for (int x = 0; x < blockGrid.length; x++) {
            for (int y = 0; y < blockGrid.length; y++) {
                if (blockGrid[x][y]) {
                    try {
                        //TODO
                        //Warum - y ?
                        if (grid[currentBlock.getX() + x][currentBlock.getY() - y] != null) {
                            return true;
                        }
                    } catch (IndexOutOfBoundsException e) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * moves the current Block sidwards if allowed
     *
     * @param offset Offset to current x-position of the block
     */
    private void moveSidewards(int offset, MoveAction moveAction) {
        currentBlock.setX(currentBlock.getX() + offset);
        if (checkForCollision()) {
            currentBlock.setX(currentBlock.getX() - offset);
        } else {
            setLastAction(moveAction);
        }
    }

    private void saveCurrenblockToGrid() {
        boolean[][] blockGrid = currentBlock.getBlockGrid();
        for (int x = 0; x < blockGrid.length; x++) {
            for (int y = 0; y < blockGrid.length; y++) {
                if (blockGrid[x][y]) {
                    grid[currentBlock.getX() + x][currentBlock.getY() - y] = currentBlock;
                }
            }
        }
    }

    //TODO refactor method that the generate RmlineAction can remove multiple lines at once, 
    //Maybe have to refactor the RmlineAction for this Reason
    private void checkForLinesToRemove() {
        ArrayList<Integer> linesToRemove = new ArrayList<Integer>();
        for (int y = gridheight; y < gridheight; y++) {
            boolean lineToRemove = true;
            for (int x = gridwidth; x < gridwidth; x++) {
                if (grid[x][y] == null) {
                    lineToRemove = false;
                }
            }
            if (lineToRemove) {
                linesToRemove.add(y);
            }
        }

        for (Integer i : linesToRemove) {
            setLastAction(new RmlineAction(0, 1, i));
        }

    }

    private void handleRotateAction(RotateAction action) {
        switch (action.getDirection()) {
            case LEFT:
                currentBlock.rotateLeft();
                if (checkForCollision()) {
                    currentBlock.rotateRight();
                } else {
                    setLastAction(action);
                }
                break;
            case RIGHT:
                currentBlock.rotateRight();
                if (checkForCollision()) {
                    currentBlock.rotateLeft();
                } else {
                    setLastAction(action);
                }
                break;
        }
    }

    private void handleHarddropAction() {
        //evaluate first how many gridfields the current stone can be moved down
        int tempY = currentBlock.getY();
        int fieldsToMove = 0;
        while (!checkForCollision()) {
            currentBlock.setY(currentBlock.getY() - 1);
            fieldsToMove++;
        }
        currentBlock.setY(tempY);
        moveDownwards(new MoveAction(0, MoveAction.Direction.DOWN, fieldsToMove));
    }

    /**
     * moves the current block downwards, the amount of gridfields moved depends on the "speed" set in the
     * moveAction
     *
     * If a collision happens the currentBlock will be moved on gridfield upwards, a new Block will be generated
     * and it will be checked if there are lines to remove
     *
     *
     * @param moveAction
     */
    private void moveDownwards(MoveAction moveAction) {
        int speed = moveAction.getSpeed();
        currentBlock.setY(currentBlock.getY() - speed);
        if (checkForCollision()) {
            currentBlock.setY(currentBlock.getY() + 1);

            saveCurrenblockToGrid();

            currentBlock = queue.getNextBlock();
            setLastAction(new NewblockAction(currentBlock, sessionId));

            checkForLinesToRemove();

            if (checkForCollision()) {
                //if collision got detected this must be becaus of the newly created block
                //TODO generate GameOverAction
            }
        } else {
            setLastAction(moveAction);
        }
    }
}
