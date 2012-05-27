package domain;

import domain.actions.*;
import domain.block.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Cyrill Lam <clam@hsr.ch>
 *
 */
public class GameEngine extends GameEngineAbstract {

    private int gridWidth = Config.gridWidth;
    private int gridHeight = Config.gridHeight;
    private int blockStartPositionX = Config.blockStartPositionX;
    private int blockStartPositionY = Config.blockStartPositionY;
    private int sessionId;
    private int score;
    private int blockCounter;
    private int level = 1;
    private int totalRemovedLines;
    private int numberOfJokers;
    private int rank;
    private String nickName = "";
    private boolean gameOver;
    private BlockAbstract[][] grid = new BlockAbstract[gridWidth][gridHeight];
    private BlockAbstract currentBlock;
    private BlockAbstract nextBlock;
    private BlockQueueInterface blockQueue;
    private Action lastAction;
    private Action lastActionForOthers;
    private List<Integer> alreadyUsedSpecialBlocks = new ArrayList<Integer>();
    private Random randomGarbageLineGenerator;

    public GameEngine(int sessionId, long seed, boolean includeSpecialBlocks, int numberOfJokers) {
        this(sessionId, seed, new BlockQueue(seed, includeSpecialBlocks), numberOfJokers);
    }

    public GameEngine(int sessionId, long seed, BlockQueueInterface blockQueue, int numberOfJokers) {
        this.sessionId = sessionId;
        this.blockQueue = blockQueue;
        this.randomGarbageLineGenerator = new Random(seed);
        this.numberOfJokers = numberOfJokers;
    }

    @Override
    public void startGame() {
        nextBlock();
    }

    private void nextBlock() {
        if (nextBlock == null) {
            currentBlock = blockQueue.getNextBlock();
        } else {
            currentBlock = nextBlock;
        }
        nextBlock = blockQueue.getNextBlock();

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
        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                if (currentBlock.equals(grid[x][y])) {
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

    private void removeAvailableLines() {
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

    private void removeLines(ArrayList<Integer> linesToRemove) {
        if (linesToRemove.size() > 1) {
            createGarbageLineAction(linesToRemove.size() - 1);
        }
        Collections.sort(linesToRemove);
        Collections.reverse(linesToRemove);
        for (Integer lineToRemove : linesToRemove) {
            //remove the lineToRemove line
            for (int x = 0; x < gridWidth; x++) {
                handleSpecialBlocks(x, lineToRemove);
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

    private void handleSpecialBlocks(int x, Integer lineToRemove) {
        if (grid[x][lineToRemove] instanceof SpecialBlockInterface && checkIfBlockOccurencesWillBeRemoved(grid[x][lineToRemove])) {
            int blockNumber = grid[x][lineToRemove].getBlockNumber();
            if (!alreadyUsedSpecialBlocks.contains(blockNumber)) {
                if (grid[x][lineToRemove] instanceof MirrorBlock) {
                    score += 300;
                    setLastActionForOthers(new MirrorAction(0, blockNumber));
                } else if (grid[x][lineToRemove] instanceof DarkBlock) {
                    score += 500;
                    setLastActionForOthers(new ShadowAction(0, blockNumber));
                }
                alreadyUsedSpecialBlocks.add(blockNumber);
            }
        }
    }

    private boolean checkIfBlockOccurencesWillBeRemoved(BlockAbstract block) {
        int counter = 0;
        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                if (grid[x][y] == block) {
                    counter++;
                    if (counter > 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private int calculateFieldsToMoveUntilCollision() {
        int tempY = currentBlock.getY();
        int fieldsToMove = 0;
        while (!checkForCollision()) {
            currentBlock.setY(currentBlock.getY() - 1);
            fieldsToMove++;
        }
        currentBlock.setY(tempY);
        return --fieldsToMove;
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
        int calculatedNewLevel = totalRemovedLines / Config.levelUpMultiplier + 1;
        if (calculatedNewLevel > level) {
            level = calculatedNewLevel;
        }
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

    @Override
    public void handleAction(Action action) {
        System.out.println("in: " + sessionId + " " + action.getType().toString() + " " + action.getTimestamp());
        if (!gameOver) {
            switch (action.getType()) {
                case MOVE:
                    handleMoveAction((MoveAction) action);
                    break;
                case ROTATION:
                    handleRotateAction((RotateAction) action);
                    break;
                case MIRROR:
                case DARK:
                    setLastAction(action);
                    alreadyUsedSpecialBlocks.add(((ActionForOthersAbstract) action).getBlockNumber());
                    break;
                case HARDDROP:
                    handleHardDropAction();
                    break;
                case GARBAGELINE:
                    handleGarbageLineAction((GarbageLineAction) action);
                    break;
                case GAMEOVER:
                    gameOver = true;
                    setLastAction(new GameOverAction(0));
                    break;
                case CLEAR:
                    if (numberOfJokers > 0) {
                        --numberOfJokers;
                        clearGrid();
                        setLastAction(action);
                    }
                    break;
            }
        } else {
            setChanged();
            notifyObservers(UpdateType.RANKING);
        }
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

    private void moveSidewards(int offset, MoveAction moveAction) {
        currentBlock.setX(currentBlock.getX() + offset);
        if (checkForCollision()) {
            currentBlock.setX(currentBlock.getX() - offset);
        } else {
            saveCurrenblockToGrid();
            setLastAction(moveAction);
        }
    }

    private void moveDownwards(MoveAction moveAction) {
        int speed = moveAction.getSpeed();
        currentBlock.setY(currentBlock.getY() - speed);
        if (checkForCollision()) {
            currentBlock.setY(currentBlock.getY() + speed);
            saveCurrenblockToGrid();
            removeAvailableLines();
            nextBlock();
        } else {
            saveCurrenblockToGrid();
            setLastAction(moveAction);
        }
    }

    private void handleHardDropAction() {
        int fieldsToMove = calculateFieldsToMoveUntilCollision();
        moveDownwards(new MoveAction(0, MoveAction.Direction.DOWN, fieldsToMove));
        score += fieldsToMove * 2;
        removeAvailableLines();
        nextBlock();
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

    private void handleGarbageLineAction(GarbageLineAction action) {
        int numberOfLines = action.getLines()[0].length;
        for (int x = 0; x < gridWidth; x++) {
            for (int y = gridHeight - 1 - numberOfLines; y >= 0; y--) {
                grid[x][y + numberOfLines] = grid[x][y];
            }
        }

        for (int x = 0; x < gridWidth; x++) {
            System.arraycopy(action.getLines()[x], 0, grid[x], 0, numberOfLines);
        }

        int oldY = currentBlock.getY();
        
        if ((oldY + numberOfLines) > (gridHeight - 1)) {
            currentBlock.setY(gridHeight - 1);
        } else {
            currentBlock.setY(oldY + numberOfLines);
        }
        
        int numbersToMove = currentBlock.getY() - oldY;
        action.setYOffsetForCurrentBlock(numbersToMove);
        setLastAction(action);
        int moveToGarbage = calculateFieldsToMoveUntilCollision();

        if (moveToGarbage > 0) {
            if (moveToGarbage <= numbersToMove) {
                moveDownwards(new MoveAction(0, MoveAction.Direction.DOWN, moveToGarbage));
            } else {
                moveDownwards(new MoveAction(0, MoveAction.Direction.DOWN, numbersToMove));
            }
        }
    }

    private void createGarbageLineAction(int numberOfLines) {
        BlockAbstract[][] garbageLines = createGarbageLine(numberOfLines, randomGarbageLineGenerator.nextInt(gridWidth));
        setLastActionForOthers(new GarbageLineAction(0, garbageLines));
    }

    public BlockAbstract[][] createGarbageLine(int numberOfLines, int emptyXPosition) {
        BlockAbstract[][] garbageLines = new BlockAbstract[gridWidth][numberOfLines];
        GarbageBlock garbageBlock = new GarbageBlock(Integer.MAX_VALUE, 0);
        for (int x = 0; x < gridWidth; ++x) {
            if (x == emptyXPosition) {
                continue;
            }
            for (int y = 0; y < numberOfLines; ++y) {
                garbageLines[x][y] = garbageBlock;
            }
        }
        return garbageLines;
    }

    private void clearGrid() {
        for (int x = 0; x < gridWidth; x++) {
            for (int y = 0; y < gridHeight; y++) {
                grid[x][y] = null;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder gridAsString = new StringBuilder();
        for (int i = gridHeight - 1; i >= 0; i--) {
            for (int j = 0; j < gridWidth; j++) {
                if (grid[j][i] != null) {
                    gridAsString.append("[").append(grid[j][i].getPrintLetter()).append("]");
                } else {
                    gridAsString.append("[ ]");
                }
            }
            gridAsString.append("\n");
        }
        gridAsString.append("\n");
        return gridAsString.toString();
    }

    @Override
    public BlockAbstract getNextBlock() {
        return (BlockAbstract) nextBlock.clone();
    }

    @Override
    public int getTotalRemovedLines() {
        return totalRemovedLines;
    }

    @Override
    public BlockAbstract getCurrentBlock() {
        return currentBlock;
    }

    public BlockAbstract[][] getGrid() {
        return grid;
    }

    public void setLastAction(Action action) {
        System.out.println("out: " + sessionId + " " + action.getType().toString() + " " + action.getTimestamp());
        lastAction = action;
        setChanged();
        notifyObservers(UpdateType.LASTACTION);
    }

    public void setLastActionForOthers(Action action) {
        System.out.println("out action for others: " + sessionId + " " + action.getType().toString() + " " + action.getTimestamp());
        lastActionForOthers = action;
        setChanged();
        notifyObservers(UpdateType.ACTIONFOROTHERS);
    }

    @Override
    public int getSessionID() {
        return sessionId;
    }

    @Override
    public Action getSimulationState() {
        return lastAction;
    }

    @Override
    public Action getlastActionForOthers() {
        return lastActionForOthers;
    }

    @Override
    public int getBlockCounter() {
        return blockCounter;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String getNickName() {
        return nickName;
    }

    @Override
    public int getNumberOfJokers() {
        return this.numberOfJokers;
    }

    @Override
    public int getRank() {
        return rank;
    }

    @Override
    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean getGameOver() {
        return gameOver;
    }
}
