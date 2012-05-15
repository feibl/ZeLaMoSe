/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.music;

import domain.SimulationStateAbstract;
import domain.actions.Action;
import domain.actions.MoveAction;
import domain.actions.RemoveLineAction;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.codecs.CodecJOrbis;
import paulscode.sound.libraries.LibraryJavaSound;

/**
 *
 * @author Cyrill
 */
public class SoundEngine implements Observer {

    private SoundSystem soundSystem;
    private boolean isGameOver = false;

    public SoundEngine() {
        try {
            SoundSystemConfig.setCodec("ogg", CodecJOrbis.class);
            soundSystem = new SoundSystem(LibraryJavaSound.class);
        } catch (SoundSystemException ex) {
            ex.printStackTrace();
            Logger.getLogger(SoundEngine.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void update(final Observable observable, Object o1) {
        if (!isGameOver) {
            final Action a = ((SimulationStateAbstract) observable).getSimulationState();

            new Thread(new Runnable() {

                @Override
                public void run() {
                    processAction(a);
                }
            }).start();
        }
    }

    public void processAction(Action action) {
        switch (action.getType()) {
            case GAMEOVER:
                stopBackGroundMusic(MusicFile.gameBackgroundMusic);
                playSound(MusicFile.gameOverSound);
                isGameOver = true;
                break;
            case HARDDROP:
                break;
            case MOVE:
                if (((MoveAction) action).getDirection() != MoveAction.Direction.DOWN) {
                    playSound(MusicFile.moveSound);
                }
                break;
            case NEWBLOCK:
                playSound(MusicFile.dropSound);
                break;
            case REMOVELINE:
                if (((RemoveLineAction) action).getLinesToRemove().size() == 4) {
                    playSound(MusicFile.fourLineRemovedSound);
                } else {
                    playSound(MusicFile.lineRemovedSound);
                }
                break;
            case ROTATION:
                playSound(MusicFile.rotateSound);
                break;
            case DARK:
                playSound(MusicFile.shadowSound);
                break;
            case MIRROR:
                playSound(MusicFile.mirrorSound);
                break;
        }
    }

    private void playSound(MusicFile sound) {
        soundSystem.quickPlay(false,
                sound.getUrl(),
                sound.getFileName(),
                false, 0, 0, 0,
                SoundSystemConfig.ATTENUATION_NONE,
                SoundSystemConfig.getDefaultAttenuation());
    }

    public void playBackgroundMusic(MusicFile backGroundMusic) {
        soundSystem.backgroundMusic(backGroundMusic.name(), backGroundMusic.getUrl(), backGroundMusic.getFileName(), true);
    }

    public void stopBackGroundMusic(MusicFile backGroundMusic) {
        soundSystem.stop(backGroundMusic.name());
    }
}
