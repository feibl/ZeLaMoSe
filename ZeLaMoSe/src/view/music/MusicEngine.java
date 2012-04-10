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
    private OggClip line4;
    private OggClip drop;
    private OggClip gameover;
    private OggClip line;
    private OggClip lobby;
    private OggClip move;
    private OggClip pause;

    public MusicEngine() {
    }

    public void startBGMusic() {
        try {
            background = new OggClip(new FileInputStream("src/view/music/files/BackgroundThemeA.ogg"));
            background.setGain(0.75f);
            background.loop();
        } catch (IOException ex) {
            Logger.getLogger(MusicEngine.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void playRotateSound() {
        try {
            rotate = new OggClip(new FileInputStream("src/view/music/files/rotation.ogg"));
            rotate.play();
        } catch (IOException ex) {
            Logger.getLogger(MusicEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void playLine4Sound() {
        try {
            line4 = new OggClip(new FileInputStream("src/view/music/files/4line.ogg"));
            line4.play();
        } catch (IOException ex) {
            Logger.getLogger(MusicEngine.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void playDropSound() {
        try {
            drop = new OggClip(new FileInputStream("src/view/music/files/drop.ogg"));
            drop.play();
        } catch (IOException ex) {
            Logger.getLogger(MusicEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void playGameOverSound() {
        try {
            gameover = new OggClip(new FileInputStream("src/view/music/files/gameover.ogg"));
            gameover.play();
        } catch (IOException ex) {
            Logger.getLogger(MusicEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void playLineSound() {
        try {
            line = new OggClip(new FileInputStream("src/view/music/files/line.ogg"));
            line.play();
        } catch (IOException ex) {
            Logger.getLogger(MusicEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void playLobbySound() {
        try {
            lobby = new OggClip(new FileInputStream("src/view/music/files/lobby.ogg"));
            lobby.loop();
        } catch (IOException ex) {
            Logger.getLogger(MusicEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void playMoveSound() {
        try {
            move = new OggClip(new FileInputStream("src/view/music/files/move.ogg"));
            move.play();
        } catch (IOException ex) {
            Logger.getLogger(MusicEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void playPauseSound() {
        try {
            pause = new OggClip(new FileInputStream("src/view/music/files/pause.ogg"));
            pause.play();
        } catch (IOException ex) {
            Logger.getLogger(MusicEngine.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
