package view.music;

import java.applet.*;
import javax.swing.*;
import java.net.*;

public class Music extends JApplet {

    public class Sound // Holds one audio file
    {

        private AudioClip song; // Sound player
        private URL songPath; // Sound path
        Sound(String filename) {
            try {
                songPath = new URL(getCodeBase(), filename); // Get the Sound URL
                song = Applet.newAudioClip(songPath); // Load the Sound

            } catch (Exception e) {
                e.printStackTrace();
            } // Satisfy the catch


        }

        public void playSound() {
            song.loop(); // Play 


        }

        public void stopSound() {
            song.stop(); // Stop


        }

        public void playSoundOnce() {
            song.play(); // Play only once

        }
    }

    @Override
    public void init() {
        Sound testsong = new Sound("src/view/music/OriginalTetrisTheme.wav");
        testsong.playSound();


    }
}
