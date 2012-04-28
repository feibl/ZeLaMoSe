/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.AbstractListModel;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class PlayerListModel extends AbstractListModel implements Observer {
    private final ConcurrentHashMap<Integer, String> playerList;

    protected PlayerListModel(ConcurrentHashMap<Integer, String> playerList) {
        this.playerList = playerList;
    }
    
    @Override
    public int getSize() {
        return playerList.size();
    }

    @Override
    public Object getElementAt(int i) {
        return new ArrayList(playerList.entrySet()).get(i);
    }

    @Override
    public void update(Observable o, Object o1) {
        fireContentsChanged(o, 0, getSize());
    }
    
}
