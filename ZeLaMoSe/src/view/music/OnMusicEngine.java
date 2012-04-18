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
public class OnMusicEngine extends MusicEngine{

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
         try {
            musicClip = new OggClip(OnMusicEngine.class.getResourceAsStream(musicFile.getMusicFile()));
        } catch (IOException exception) {
            musicClip = null;
            System.out.println("Music file: " + musicFile.toString() + " (" + musicFile.getMusicFile() + ") was not found!");
        }finally {
             return musicClip;
         }
        
    }
}
