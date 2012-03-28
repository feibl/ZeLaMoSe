/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.actions;

import java.awt.event.KeyEvent;

/**
 * Class which holds only a Key_Event (which user pressed) and the absolute timestamp
 * @author Patrick Zenhäusern
 */
public class InputEvent {
    private int keyEvent;
    private long absoluteTimeStamp;

    public long getAbsoluteTimeStamp() {
        return absoluteTimeStamp;
    }

    public int getKeyEvent() {
        return keyEvent;
    }

    public InputEvent(KeyEvent keyEvent, long absoluteTimeStamp) {
        this.keyEvent = keyEvent.getKeyCode();
        this.absoluteTimeStamp = absoluteTimeStamp;
    }
    
    
}
