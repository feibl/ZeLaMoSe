package domain;

import domain.actions.MoveAction;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import view.music.SoundEngine;

/**
 *
 * @author Christian Mollekopf <cmolleko@hsr.ch>
 */
public class SoundEnginePerformance {

    private SoundEngine soundEngine;

    @Before
    public void setUp() {
        soundEngine = new SoundEngine();
    }

    @Test
    public void testDelay() {
        for (int i = 0; i < 10; i++) {
            long timeBefore = System.currentTimeMillis();
            soundEngine.processAction(new MoveAction(0, MoveAction.Direction.LEFT, 0));
            //soundEngine.processAction(new MirrorAction(0, 1));
            long timeAfter = System.currentTimeMillis();
            System.out.println("Delay(ms): " + (timeAfter - timeBefore));
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(SoundEnginePerformance.class.getName()).log(Level.SEVERE, null, ex);
//            }
            assertTrue(timeAfter - timeBefore < 50);
        }
    }
}
