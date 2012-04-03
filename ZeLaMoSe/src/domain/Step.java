/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.actions.Action;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author chrigi
 */
public class Step implements Serializable {
    private int sequenceNumber;
    private int sessionID;
    private ArrayList<Action> actions;

    public Step(int seqNum, int sessionId) {
        this.sequenceNumber = seqNum;
        this.sessionID = sessionId;
        actions = new ArrayList<Action>();
    }
    
    public int getSequenceNumber() {
        return sequenceNumber;
    }
    
//    public void setSessionId(int id) {
//        this.getSessionID = id;
//    }
    
    public int getSessionID() {
        return sessionID;
    }
    
    public void addAction(Action action) {
        actions.add(action);
    }
    
    public ArrayList<Action> getActions() {
        return actions;
    }

    
}
