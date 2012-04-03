/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.music;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 *
 * @author Patrick Zenhäusern
 */
public class Music extends Thread {

    private File file;
    private AudioStream as;
    private AudioPlayer p;
    private boolean playback;
    private boolean doLoop;

    public Music(String file, boolean doLoop) {
        this.doLoop = doLoop;
        this.file = new File(file);
    }

    private void createNewAudioStream(File file) {
        try {
            as = new AudioStream(new FileInputStream(file));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        startPlayback();
    }

    public void startPlayback() {
        playback = true;
        createNewAudioStream(file);
        p.player.start(as);
        if (doLoop) {
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

    public void stopPlayback() {
        playback = false;
        p.player.stop(as);
    }
}
