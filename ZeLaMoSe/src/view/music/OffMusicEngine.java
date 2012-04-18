/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.music;

import org.newdawn.easyogg.OggClip;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class OffMusicEngine extends MusicEngine {

    @Override
    public void playMusic(boolean doLoop, float gain) {
    }

    @Override
    public void stopMusic() {
    }

    @Override
    protected OggClip initMusicClip(MusicFile musicFile) {
        return null;
    }

    
}
