package domain;

/**
 *
 * @author Christian Mollekopf <cmolleko@hsr.ch>
 */
public interface StepProducerInterface {

    public Step getStep();
    //Called every 50ms

    public void processStep();
}
