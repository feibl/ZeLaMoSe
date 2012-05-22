package domain;

import java.util.Observable;

/**
 *
 * @author Christian Mollekopf <cmolleko@hsr.ch>
 */
abstract public class StepGeneratorAbstract extends Observable implements StepProducerInterface {

    abstract public void setSessionID(int sessionID);

    abstract public InputSamplerInterface getInputSampler();
}
