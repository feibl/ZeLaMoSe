/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import domain.ChatController;
import domain.TetrisController;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.AbstractListModel;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import network.SessionInformation;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class PlayerListModel extends AbstractListModel implements Observer {

    private final Map<Integer, String> playerList;
    private final ChatController chatController;

    protected PlayerListModel(ChatController chatController) {
        this.playerList = new HashMap<Integer, String>(chatController.getSessionList());
        this.chatController = chatController;
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
        int changedIndex;
        switch ((TetrisController.UpdateType) o1) {
            case SESSION_ADDED: {
                final SessionInformation addedSession = chatController.getAddedSession();
                playerList.put(addedSession.getId(), addedSession.getNickname());
                changedIndex = new ArrayList(playerList.keySet()).indexOf(addedSession.getId());
                fireIntervalAdded(this, changedIndex, changedIndex);
                break;
            }
            case SESSION_REMOVED: {
                final SessionInformation removedSession = chatController.getRemovedSession();
                changedIndex = new ArrayList(playerList.keySet()).indexOf(removedSession.getId());
                playerList.remove(removedSession.getId());
                fireIntervalRemoved(this, changedIndex, changedIndex);
                break;
            }
        }
        fireContentsChanged(o, 0, getSize());
    }
}
