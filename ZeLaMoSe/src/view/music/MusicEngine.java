/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.music;

import org.newdawn.easyogg.OggClip;

/**
 *
 * @author Cyrill
 */
public abstract class MusicEngine {
    
    protected OggClip musicClip = null;
    protected MusicFile musicFile;

    public abstract void playMusic(boolean doLoop, float gain);
    
    protected abstract OggClip initMusicClip(MusicFile musicFile);
    public void playMusic(boolean doLoop) {
        playMusic(doLoop, 1.0f);
    }

    public abstract void stopMusic();
}
