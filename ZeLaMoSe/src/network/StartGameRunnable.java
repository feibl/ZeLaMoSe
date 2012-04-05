/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network;

import java.rmi.RemoteException;
import network.server.GameServerImpl;
import network.server.Session;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class StartGameRunnable implements Runnable {
    private GameServerImpl gameServer;
    private Session[] sessionList;

    public StartGameRunnable(GameServerImpl gameServer, Session[] sessionList) {
        this.gameServer = gameServer;
        this.sessionList = sessionList;
    }

    @Override
    public void run() {
        for (int i = 0; i < sessionList.length; i++) {
            Session s = sessionList[i];
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
