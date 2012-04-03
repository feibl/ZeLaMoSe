/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.interfaces.BlockQueueInterface;
import domain.interfaces.GameEngineInterface;
import domain.actions.*;
import domain.block.Block;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;

/**
 *
 * @author Cyrill
 * 
 * bei Action: wird momentan für den timestamp SESSIONID mitgegeben
 */
public class GameEngine extends GameEngineInterface {

    private Action lastAction;
    private int sessionId;
    private Block[][] grid;
    int gridwidth = 12, gridheight = 24, defaultX = 4, defaultY = 0;
    private BlockQueueInterface queue;
    private Block currentBlock;
    private boolean gameOver;

    public boolean isGameOver() {
        return gameOver;
    }

    public GameEngine(int sessionId) {
        this(sessionId, System.nanoTime());
    }

    public GameEngine(int sessionId, long seed) {
        this.sessionId = sessionId;
        grid = new Block[gridwidth][gridheight];
        queue = new BlockQueue(seed);
    }

    public Block getCurrentBlock() {
        return currentBlock;
    }

    @Override
    public void startGame() {
        nextBlock();
    }

    //Called to fake a blockqueue
    public GameEngine(int sessionId, long seed, BlockQueueInterface fakeQueue) {
        this.sessionId = sessionId;
        grid = new Block[gridwidth][gridheight];
        queue = fakeQueue;
    }

    public Block[][] getGrid() {
        return grid;
    }

    public void setLastAction(Action action) {
        lastAction = action;
        setChanged();
        notifyObservers();
    }

    @Override
    public int getSessionID() {
        return sessionId;
    }

    @Override
    public void handleAction(Action action) {
        switch (action.getType()) {
            case MOVE:
                handleMoveAction((MoveAction) action);
                break;
            case ROTATION:
                handleRotateAction((RotateAction) action);
                break;
            case HARDDROP:
                handleHardDropAction();
                break;

        }
    }

    @Override
    public Action getSimulationState() {
        return lastAction;
    }

    private boolean checkForGameOver() {
        for (int y = 0; y < currentBlock.getHeight(); y++) {
            for (int x = 0; x < currentBlock.getWidth(); x++) {
                if (grid[defaultX + x][defaultY + y] != null) {
                    gameOver = true;
                    System.out.println("Game Over");
                    break;
                }
            }
            if (gameOver) {
                break;
            }
        }
        return gameOver;
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
        boolean[][] blockGrid = currentBlock.getGrid();
        for (int x = 0; x < blockGrid.length; x++) {
            for (int y = 0; y < blockGrid.length; y++) {
                if (blockGrid[x][y]) {
                    try {
                        if (grid[currentBlock.getX() + x][currentBlock.getY() + y] != null && !grid[currentBlock.getX() + x][currentBlock.getY() + y].equals(currentBlock)) {
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
                    saveCurrenblockToGrid();
            setLastAction(moveAction);
        }
    }

    private void nextBlock() {
        currentBlock = queue.getNextBlock();
        if (!checkForGameOver()) {
            currentBlock.setX(defaultX);
            currentBlock.setY(defaultY);
                    saveCurrenblockToGrid();
            setLastAction(new NewBlockAction(currentBlock, sessionId));
        }else {
            setLastAction(new GameOverAction(sessionId));
        }
    }

    private void saveCurrenblockToGrid() {
        boolean[][] blockGrid = currentBlock.getGrid();
        //Refactor find a better way to delte the old block references
        for (int x = 0; x < gridwidth; x++) {
            for (int y = 0; y < gridheight; y++) {
                if (grid[x][y] != null && grid[x][y].equals(currentBlock)) {
                    grid[x][y] = null;
                }
            }
        }

        for (int x = 0; x < blockGrid.length; x++) {
            for (int y = 0; y < blockGrid.length; y++) {
                if (blockGrid[x][y]) {
                    grid[currentBlock.getX() + x][currentBlock.getY() + y] = currentBlock;
                }
            }
        }
    }

    //TODO refactor method that the generate RemoveLineAction can remove multiple lines at once, 
    //Maybe have to refactor the RemoveLineAction for this Reason
    private void checkForLinesToRemove() {
        boolean removeLine;
        ArrayList<Integer> linesToRemove = new ArrayList<Integer>();
        for (int y = gridheight - 1; y > 0; y--) {
            removeLine = true;
            for (int x = 0; x < gridwidth; x++) {
                if (grid[x][y] == null) {
                    removeLine = false;
                }
            }
            if (removeLine) {
                linesToRemove.add(y);
            }
        }

        if (linesToRemove.size() > 0) {
            removeLines(linesToRemove);
        }

    }

    private void handleRotateAction(RotateAction action) {
        switch (action.getDirection()) {
            case LEFT:
                currentBlock.rotateLeft();
                if (checkForCollision()) {
                    currentBlock.rotateRight();
                } else {
                            saveCurrenblockToGrid();
                    setLastAction(action);
                }
                break;
            case RIGHT:
                currentBlock.rotateRight();
                if (checkForCollision()) {
                    currentBlock.rotateLeft();
                } else {
                            saveCurrenblockToGrid();
                    setLastAction(action);
                }
                break;
        }
    }

    private void handleHardDropAction() {
        //evaluate first how many gridfields the current stone can be moved down
        int tempY = currentBlock.getY();
        int fieldsToMove = 0;
        while (!checkForCollision()) {
            currentBlock.setY(currentBlock.getY() + 1);
            fieldsToMove++;
        }
        fieldsToMove--;
        currentBlock.setY(tempY);
        moveDownwards(new MoveAction(0, MoveAction.Direction.DOWN, fieldsToMove));
        checkForLinesToRemove();
        nextBlock();
    }

    public void print() {
        for (int i = 0; i < gridheight; i++) {
            String lineOutput = "";
            for (int j = 0; j < gridwidth; j++) {
                if (grid[j][i] != null) {
                    lineOutput += "[" + grid[j][i].getPrintLetter() + "]";
                } else {
                    lineOutput += "[ ]";
                }
            }
            System.out.println(lineOutput);
        }
        System.out.println("");
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
        currentBlock.setY(currentBlock.getY() + speed);
        if (checkForCollision()) {
            currentBlock.setY(currentBlock.getY() - speed);
            saveCurrenblockToGrid();
            checkForLinesToRemove();
            nextBlock();
        } else {
            saveCurrenblockToGrid();
            setLastAction(moveAction);
        }
    }

    private void removeLines(ArrayList<Integer> linesToRemove) {

        Collections.sort(linesToRemove);
        for (Integer lineToRemove : linesToRemove) {
            //remove the lineToRemove line
            for (int x = 0; x < gridwidth; x++) {
                grid[x][lineToRemove] = null;
            }

            //move everythign downward
            for (int y = lineToRemove - 1; y >= 0; y--) {
                for (int x = 0; x < gridwidth; x++) {
                    grid[x][y + 1] = grid[x][y];
                }
            }
        }
        if (linesToRemove.size() > 1) {
            System.out.println("multi lines remove: " + linesToRemove.size());
        }
        setLastAction(new RemoveLineAction(0, linesToRemove));


    }
}
