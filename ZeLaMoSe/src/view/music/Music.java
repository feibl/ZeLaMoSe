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
public abstract class Music extends Thread {

    protected File file;
    protected AudioStream as;
    protected AudioPlayer p;
    protected boolean playback;

    public Music(String file) {
        this.file = new File(file);
        playback = true;
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

    public abstract void startPlayback();

    public void stopPlayback() {
        playback = false;
        p.player.stop(as);
    }
}
