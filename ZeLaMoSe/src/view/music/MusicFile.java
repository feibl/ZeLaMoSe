/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.music;

import domain.Config;

/**
 *
 * @author Patrick Zenhäusern
 */
public enum MusicFile {
    gameBackgroundMusic("BackgroundThemeA.ogg"),
    mainBackgroundMusic("Niggas_In_Paris.ogg"),
    lobbyBackgroundMusic("lobby.ogg"),
    rotateSound("rotation.ogg"),
    fourLineRemovedSound("4line.ogg"),
    lineRemovedSound("line.ogg"),
    dropSound("drop.ogg"),
    gameOverSound("gameover.ogg"),
    moveSound("move.ogg")
     ;
    
    private String musicFile;
    private MusicFile(String musicFile) {
        this.musicFile = Config.basicPathToMusicFiles + musicFile;
    }
    
    public String getMusicFile(){
        return musicFile;
    }
}
