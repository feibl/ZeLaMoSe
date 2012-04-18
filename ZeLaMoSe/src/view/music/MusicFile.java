/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.music;

/**
 *
 * @author Patrick Zenhäusern
 */
public enum MusicFile {
    gameBackgroundMusic("/view/music/files/BackgroundThemeA.ogg"),
    mainBackgroundMusic("/view/music/files/Niggas_In_Paris.ogg"),
    lobbyBackgroundMusic("/view/music/files/lobby.ogg"),
    rotateSound("/view/music/files/rotation.ogg"),
    fourLineRemovedSound("/view/music/files/4line.ogg"),
    lineRemovedSound("/view/music/files/line.ogg"),
    dropSound("/view/music/files/drop.ogg"),
    gameOverSound("/view/music/files/gameover.ogg"),
    moveSound("/view/music/files/move.ogg")
     ;
    
    private String musicFile;
    private MusicFile(String musicFile) {
        this.musicFile = musicFile;
    }
    
    public String getMusicFile(){
        return musicFile;
    }
}
