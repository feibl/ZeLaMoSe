package domain.fake;

import domain.InputSampler;
import domain.Step;
import domain.StepGenerator;
import domain.TetrisController;
import java.awt.event.KeyEvent;

/**
 *
 * @author Christian Mollekopf <cmolleko@hsr.ch>
 */
public class FakeStepGenerator extends StepGenerator {

    public Step step;

    public FakeStepGenerator() {
        super(new InputSampler() {

            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                return false;
            }
        });
    }

    @Override
    public void setSessionID(int sessionID) {
        //do nothing
    }

    @Override
    public void processStep() {
        setChanged();
        notifyObservers(TetrisController.UpdateType.STEP);
    }

    @Override
    public Step getStep() {
        return step;
    }

    @Override
    public InputSampler getInputSampler() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
