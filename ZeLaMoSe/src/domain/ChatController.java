/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;
import network.client.NetworkHandler;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class ChatController extends Observable implements Observer {

    private NetworkHandler nH;
    private ConcurrentHashMap<Integer, String> sessionMap;

    @Override
    public void update(Observable o, Object o1) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
