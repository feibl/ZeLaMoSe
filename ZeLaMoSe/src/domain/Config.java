/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 *
 * @author Patrick Zenhäusern
 */
public class Config {

    public final static int gridWidth = 12;
    public final static int gridHeight = 24;
    public final static int gridBlockWidth = 4;
    public final static int gridBlockHeight = 4;
    public final static int levelUpMultiplier = 10;
    public final static int defaultWallKickTest = 1;
    public final static int maxLevelForSpeed = 15;
    public final static int advanceStepLimit = 21;
    public final static int frameRate = 30;
    public final static int ownGameFieldBlockSize = 30;
    public final static int EnemyGameFieldBlockSize = 10;
    public final static String basicPathToMusicFiles = "/resource/music/";
    public final static int blockStartPositionX = 4;
    public final static int blockStartPositionY = gridHeight - 1;

    public static File convertRMI(Class c) {
        File tf = null;
        try {
            tf = File.createTempFile(c.getSimpleName().toString(), ".policy");
            tf.deleteOnExit();
            byte buffer[] = new byte[0x1000];
            InputStream in = c.getResourceAsStream("/resource/rmi/rmi.policy");
            FileOutputStream out = new FileOutputStream(tf);
            int cnt;
            while ((cnt = in.read(buffer)) != -1) {
                out.write(buffer, 0, cnt);
            }
            in.close();
            out.close();
        } catch (Exception e) {
            System.out.println("RMI file not found");
        } finally {
            return tf;
        }
    }
}
