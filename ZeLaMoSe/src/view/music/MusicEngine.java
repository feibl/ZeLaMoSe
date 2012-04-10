/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.music;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.easyogg.OggClip;

/**
 *
 * @author Cyrill
 */
public class MusicEngine {
    private OggClip background;
    private OggClip rotate;

    public MusicEngine() {
        try {
            background = new OggClip(new FileInputStream("src/view/music/files/BackgroundThemeA.ogg"));
            background.setGain(0.8f);
        } catch (IOException ex) {
            Logger.getLogger(MusicEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            rotate = new OggClip(new FileInputStream("src/view/music/files/rotate.ogg"));
        } catch (IOException ex) {
            Logger.getLogger(MusicEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    

    public void startBGMusic() {
       if(background!=null)
           background.loop();
    }
    
    public void playRotateSound(){
        
        if(rotate!=null)
            rotate.play();
    }
}
