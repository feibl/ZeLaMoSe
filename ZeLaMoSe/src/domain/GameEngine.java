/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.actions.*;
import domain.block.BlockAbstract;
import domain.block.GarbageBlock;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Cyrill Lam <clam@hsr.ch>
 *
 */
public class GameEngine extends GameEngineAbstract {

    private Action lastAction;
    private int sessionId;
    private BlockAbstract[][] grid;
    private int gridWidth = Config.gridWidth, gridHeight = Config.gridHeight;
    private int blockStartPositionX = Config.blockStartPositionX, blockStartPositionY = Config.blockStartPositionY;
    private BlockQueueInterface queue;
    private BlockAbstract currentBlock;
    private boolean gameOver;
    private int score;
    private BlockAbstract nextBlock;
    private int blockCounter = 0;
    private SimulationController simulationController;
    private Random randomNewLineGenerator;
    private String nickName = "";
    private int level;
    private int totalRemovedLines;

    public GameEngine(int sessionId, long seed) {
        this(sessionId, seed, new BlockQueue(seed));
    }

    public GameEngine(int sessionId, long seed, BlockQueueInterface fakeQueue) {
        this.sessionId = sessionId;
        grid = new BlockAbstract[gridWidth][gridHeight];
        queue = fakeQueue;
        score = 0;
        level = 1;
        totalRemovedLines = 0;
        randomNewLineGenerator = new Random(seed);
    }

    public BlockAbstract getNextBlock() {
        return (BlockAbstract) nextBlock.clone();
    }

    @Override
    public int getLevel() {
        return level;
    }

    public int getScore() {
        return score;
    }

    public int getTotalRemovedLines() {
        return totalRemovedLines;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public BlockAbstract getCurrentBlock() {
        return currentBlock;
    }

    @Override
    public void startGame() {
        nextBlock();
    }

    public BlockAbstract[][] getGrid() {
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
        if (!gameOver) {
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
                case NEWLINE:
                    handleGarbageLineAction((GarbageLineAction) action);
                    break;
            }
        }
    }

    @Override
    public Action getSimulationState() {
        return lastAction;
    }

    private void calculatePlayerStats(ArrayList<Integer> linesToRemove) {
        //Calculate the score
        switch (linesToRemove.size()) {
            case 1:
                score += 100 * level;
                break;
            case 2:
                score += 300 * level;
                break;
            case 3:
                score += 500 * level;
                break;
            default:
                score += 800 * level;
                break;
        }
        totalRemovedLines += linesToRemove.size();
        //Calculate the new level
        level = totalRemovedLines / Config.levelUpMultiplier + 1;
    }

    private boolean checkForGameOver() {
        for (int y = 0; y < currentBlock.getHeight(); y++) {
            for (int x = 0; x < currentBlock.getWidth(); x++) {
                if (grid[blockStartPositionX + x][blockStartPositionY - y] != null) {
                    gameOver = true;
                    return gameOver;
                }
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
        BlockAbstract[][] blockGrid = currentBlock.getGrid();
        for (int x = 0; x < blockGrid.length; x++) {
            for (int y = 0; y < blockGrid.length; y++) {
                if (blockGrid[x][y] != null) {
                    try {
                        if (grid[currentBlock.getX() + x][currentBlock.getY() - y] != null && !grid[currentBlock.getX() + x][currentBlock.getY() - y].equals(currentBlock)) {
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
        if (nextBlock == null) {
            currentBlock = queue.getNextBlock();
        } else {
            currentBlock = nextBlock;
        }
        nextBlock = queue.getNextBlock();

        if (!checkForGameOver()) {
            currentBlock.setX(blockStartPositionX);
            currentBlock.setY(blockStartPositionY);
            saveCurrenblockToGrid();
            setLastAction(new NewBlockAction(currentBlock, sessionId));
            ++blockCounter;
        } else {
            setLastAction(new GameOverAction(sessionId));
        }
    }

    private void saveCurrenblockToGrid() {
        BlockAbstract[][] blockGrid = currentBlock.getGrid();
        //Refactor find a better way to delte the old block references
        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                if (grid[x][y] != null && grid[x][y].equals(currentBlock)) {
                    grid[x][y] = null;
                }
            }
        }

        for (int x = 0; x < blockGrid.length; x++) {
            for (int y = 0; y < blockGrid.length; y++) {
                if (blockGrid[x][y] != null) {
                    grid[currentBlock.getX() + x][currentBlock.getY() - y] = currentBlock;
                }
            }
        }
    }

    private void checkForLinesToRemove() {
        boolean removeLine;
        ArrayList<Integer> linesToRemove = new ArrayList<Integer>();
        for (int y = 0; y < gridHeight; y++) {
            removeLine = true;
            for (int x = 0; x < gridWidth; x++) {
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
        int originX = currentBlock.getX();
        int originY = currentBlock.getY();
        //for with 5 passes, for each test
        for (int wallKickTestNumber = 1; wallKickTestNumber <= 5; wallKickTestNumber++) {
            if (action.getDirection() == RotateAction.Direction.LEFT) {
                currentBlock.rotateLeft(wallKickTestNumber);
            } else {
                currentBlock.rotateRight(wallKickTestNumber);
            }
            if (checkForCollision()) {
                if (action.getDirection() == RotateAction.Direction.LEFT) {
                    currentBlock.rotateRight(wallKickTestNumber);
                } else {
                    currentBlock.rotateLeft(wallKickTestNumber);
                }
                currentBlock.setX(originX);
                currentBlock.setY(originY);
            } else {
                saveCurrenblockToGrid();
                action.setXOffset(currentBlock.getX() - originX);
                action.setYOffset(currentBlock.getY() - originY);
                setLastAction(action);
                return;
            }
        }
    }

    private void handleHardDropAction() {
        //evaluate first how many gridfields the current stone can be moved down
        int tempY = currentBlock.getY();
        int fieldsToMove = 0;
        while (!checkForCollision()) {
            currentBlock.setY(currentBlock.getY() - 1);
            fieldsToMove++;
        }
        fieldsToMove--;
        currentBlock.setY(tempY);
        moveDownwards(new MoveAction(0, MoveAction.Direction.DOWN, fieldsToMove));
        checkForLinesToRemove();
        nextBlock();
    }

    public void print() {
        for (int i = gridHeight - 1; i >= 0; i--) {
            String lineOutput = "";
            for (int j = 0; j < gridWidth; j++) {
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

    private void moveDownwards(MoveAction moveAction) {
        int speed = moveAction.getSpeed();
        currentBlock.setY(currentBlock.getY() - speed);
        if (checkForCollision()) {
            currentBlock.setY(currentBlock.getY() + speed);
            saveCurrenblockToGrid();
            checkForLinesToRemove();
            nextBlock();
        } else {
            saveCurrenblockToGrid();
            setLastAction(moveAction);
        }
    }

    private void removeLines(ArrayList<Integer> linesToRemove) {
        if (linesToRemove.size() > 1) {
            createGarbageLineAction(linesToRemove.size() - 1);
        }
        Collections.sort(linesToRemove);
        Collections.reverse(linesToRemove);
        for (Integer lineToRemove : linesToRemove) {
            //remove the lineToRemove line
            for (int x = 0; x < gridWidth; x++) {
                grid[x][lineToRemove] = null;
            }

            //move everythign downward
            for (int y = lineToRemove + 1; y < gridHeight; y++) {
                for (int x = 0; x < gridWidth; x++) {
                    grid[x][y - 1] = grid[x][y];
                }
            }
        }
        calculatePlayerStats(linesToRemove);
        setLastAction(new RemoveLineAction(0, linesToRemove));
    }

    public int getBlockCounter() {
        return blockCounter;
    }

    private void createGarbageLineAction(int numberOfLines) {
        BlockAbstract[][] garbageLines = new BlockAbstract[gridWidth][numberOfLines];

        int emptyXPosition = randomNewLineGenerator.nextInt(gridWidth);
        GarbageBlock garbageBlock = new GarbageBlock();
        for (int x = 0; x < gridWidth; ++x) {
            if (x == emptyXPosition) {
                continue;
            }
            for (int y = 0; y < numberOfLines; ++y) {
                garbageLines[x][y] = garbageBlock;
            }
        }
        if (simulationController != null) {
            simulationController.addGarbageLineAction(sessionId, new GarbageLineAction(0, garbageLines));
        }
    }

    private void handleGarbageLineAction(GarbageLineAction action) {
        int numberOfLines = action.getLines()[0].length;
        for (int x = 0; x < gridWidth; x++) {
            for (int y = gridHeight - 1 - numberOfLines; y >= 0; y--) {
                grid[x][y + numberOfLines] = grid[x][y];
            }
        }

        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < numberOfLines; y++) {
                if (action.getLines()[x][y] != null) {
                    grid[x][y] = action.getLines()[x][y];
                } else {
                    grid[x][y] = null;
                }
            }
        }
        setLastAction(action);
    }

    @Override
    public void setSimulationController(SimulationController simulationController) {
        this.simulationController = simulationController;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
