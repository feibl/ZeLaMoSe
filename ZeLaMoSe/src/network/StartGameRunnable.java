/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.rmi.RemoteException;
import network.server.GameServer;
import network.server.SessionInterface;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class StartGameRunnable implements Runnable {
    private GameServer gameServer;
    private SessionInterface[] sessionList;

    public StartGameRunnable(GameServer gameServer, SessionInterface[] sessionList) {
        this.gameServer = gameServer;
        this.sessionList = sessionList;
    }

    @Override
    public void run() {
        for (int i = 0; i < sessionList.length; i++) {
            SessionInterface s = sessionList[i];
            if (s != null) {
                try {
                    s.sendStartSignal();
                } catch (RemoteException ex) {
                    gameServer.removeSession(s);
                }
            }
        }
    }
    
}
