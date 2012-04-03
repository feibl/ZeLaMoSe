package view.music;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 *
 * @author Mangusbrother
 */
public class LoopMusic  extends Music{


    public LoopMusic(String file) {
        super(file);
    }
        
    @Override
    public void startPlayback() {
        p.player.start(as);
        try {
            do {
            } while (as.available() > 0 && playback);
            if (playback) {
                startPlayback();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

}