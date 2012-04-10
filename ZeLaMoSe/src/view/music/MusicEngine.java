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
        try {
            background = new OggClip(new FileInputStream("src/view/music/files/BackgroundThemeA.ogg"));
            background.setGain(0.75f);
        } catch (IOException ex) {
            Logger.getLogger(MusicEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            rotate = new OggClip(new FileInputStream("src/view/music/files/rotation.ogg"));
        } catch (IOException ex) {
            Logger.getLogger(MusicEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            line4 = new OggClip(new FileInputStream("src/view/music/files/4line.ogg"));
        } catch (IOException ex) {
            Logger.getLogger(MusicEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            drop = new OggClip(new FileInputStream("src/view/music/files/drop.ogg"));
        } catch (IOException ex) {
            Logger.getLogger(MusicEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            gameover = new OggClip(new FileInputStream("src/view/music/files/gameover.ogg"));
        } catch (IOException ex) {
            Logger.getLogger(MusicEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            line = new OggClip(new FileInputStream("src/view/music/files/line.ogg"));
        } catch (IOException ex) {
            Logger.getLogger(MusicEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            lobby = new OggClip(new FileInputStream("src/view/music/files/lobby.ogg"));
        } catch (IOException ex) {
            Logger.getLogger(MusicEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            move = new OggClip(new FileInputStream("src/view/music/files/move.ogg"));
        } catch (IOException ex) {
            Logger.getLogger(MusicEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            pause = new OggClip(new FileInputStream("src/view/music/files/pause.ogg"));
        } catch (IOException ex) {
            Logger.getLogger(MusicEngine.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void startBGMusic() {
        if (background != null) {
            background.loop();
        }
    }

    public void playRotateSound() {

        if (rotate != null) {
            rotate.play();
        }
    }

    public void playLine4Sound() {

        if (line4 != null) {
            line4.play();
        }
    }

    public void playDropSound() {

        if (drop != null) {
            drop.play();
        }
    }

    public void playGameOverSound() {

        if (gameover != null) {
            gameover.play();
        }
    }

    public void playLineSound() {

        if (line != null) {
            line.play();
        }
    }

    public void playLobbySound() {

        if (lobby != null) {
            lobby.loop();
        }
    }

    public void playMoveSound() {

        if (move != null) {
            move.play();
        }
    }

    public void playPauseSound() {

        if (pause != null) {
            pause.play();
        }
    }
}
