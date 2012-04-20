/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.music;

import java.io.IOException;
import org.newdawn.easyogg.OggClip;

/**
 *
 * @author Cyrill
 */
public class OnMusicEngine extends MusicEngine {

    private boolean fileExists = true;

    public OnMusicEngine(MusicFile musicFile) {
        this.musicFile = musicFile;
    }

    @Override
    public void playMusic(boolean doLoop, float gain) {
        if (initMusicClip(musicFile) != null) {
            musicClip.setGain(gain);
            if (doLoop) {
                musicClip.loop();
            } else {
                musicClip.play();
            }
        }
    }

    @Override
    public void stopMusic() {
        if (musicClip != null) {
            musicClip.stop();
        }
    }

    @Override
    protected OggClip initMusicClip(MusicFile musicFile) {
        //If we already tested before that the file is inexistent there's no need to do it again
        if (!fileExists) {
            return null;
        }
        try {
            musicClip = new OggClip(OnMusicEngine.class.getResourceAsStream(musicFile.getMusicFile()));
        } catch (IOException exception) {
            musicClip = null;
            fileExists = false;
            System.out.println("Music file: " + musicFile.toString() + " (" + musicFile.getMusicFile() + ") was not found!");
        } finally {
            return musicClip;
        }

    }
}
