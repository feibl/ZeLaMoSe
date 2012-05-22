package domain;

import domain.actions.*;
import java.awt.event.KeyEvent;

/**
 *
 * @author Patrick Zenhäusern <pzenhaeu@hsr.ch>
 *
 */
public class StepGenerator extends StepGeneratorAbstract {

    private InputSamplerInterface inputSampler;
    private Step step;
    private int counter = 0;
    private int sessionID;

    public StepGenerator(InputSampler inputsampler) {
        this.inputSampler = inputsampler;
    }

    public StepGenerator(int sessionID, InputSamplerInterface inputsampler) {
        this.sessionID = sessionID;
        this.inputSampler = inputsampler;
    }

    @Override
    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    @Override
    public InputSamplerInterface getInputSampler() {
        return inputSampler;
    }

    @Override
    public void processStep() {
        step = new Step(counter++, sessionID);
        long currentTime = System.nanoTime();
        for (InputEvent ie : inputSampler.getAndRemoveAll()) {
            long relativeTime = (currentTime - ie.getAbsoluteTimeStamp());
            switch (ie.getKeyEvent()) {
                case KeyEvent.VK_LEFT:
                    step.addAction(new MoveAction(relativeTime, MoveAction.Direction.LEFT, 1));
                    break;
                case KeyEvent.VK_RIGHT:
                    step.addAction(new MoveAction(relativeTime, MoveAction.Direction.RIGHT, 1));
                    break;
                case KeyEvent.VK_DOWN:
                    step.addAction(new MoveAction(relativeTime, MoveAction.Direction.DOWN, 1));
                    break;
                case KeyEvent.VK_Y:
                    step.addAction(new RotateAction(relativeTime, RotateAction.Direction.LEFT));
                    break;
                case KeyEvent.VK_X:
                    step.addAction(new RotateAction(relativeTime, RotateAction.Direction.RIGHT));
                    break;
                case KeyEvent.VK_SPACE:
                case KeyEvent.VK_UP:
                    step.addAction(new HardDropAction(relativeTime));
                    break;
                case KeyEvent.VK_J:
                    step.addAction(new ClearAction(relativeTime));
                    break;
            }
        }
        setChanged();
        notifyObservers(TetrisController.UpdateType.STEP);
    }

    @Override
    public Step getStep() {
        return step;
    }
}
