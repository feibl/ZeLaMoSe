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
public class SingleMusic  extends Music{

    public SingleMusic(String file) {
        super(file);
    }
        
    @Override
    public void startPlayback() {
        p.player.start(as);
    }

}