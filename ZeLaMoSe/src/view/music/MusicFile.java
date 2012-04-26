/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.music;

import java.net.URL;

/**
 *
 * @author Patrick Zenhäusern <pzenhaeu@hsr.ch>
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
    private final static String basicPathToMusicFiles = "resource/music/";
    private URL url;
    private String fileName;
    private MusicFile(String musicFile) {
        fileName = musicFile;
        this.url = this.getClass().getClassLoader().getResource(basicPathToMusicFiles + fileName);
    }
    
    public String getFileName(){
        return fileName;
    }

    public URL getUrl() {
        return url;
    }
    
}
