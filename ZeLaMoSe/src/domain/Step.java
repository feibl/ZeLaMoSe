/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.actions.Action;
import java.util.ArrayList;

/**
 *
 * @author chrigi
 */
public class Step {
    private int seqNum;
    private int sessionId;
    private ArrayList<Action> actions;

    public Step(int seqNum, int sessionId) {
        this.seqNum = seqNum;
        this.sessionId = sessionId;
        actions = new ArrayList<>();
    }
    
    public int seqNum() {
        return seqNum;
    }
    
//    public void setSessionId(int id) {
//        this.sessionId = id;
//    }
    
    public int sessionId() {
        return sessionId;
    }
    
    public void addAction(Action action) {
        actions.add(action);
    }
    
    public ArrayList<Action> actions() {
        return actions;
    }

    
}
