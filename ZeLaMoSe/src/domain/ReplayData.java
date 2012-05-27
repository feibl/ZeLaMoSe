/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.util.*;
import network.GameParams;

/**
 *
 * @author feibl
 */
public class ReplayData implements Serializable {
    
    SortedMap<Integer, List<Step>> steps = new TreeMap<Integer, List<Step>>();
    Map<Integer, String> sessionList;
    network.GameParams gameParams;
    int ownSessionId;

    public SortedMap<Integer, List<Step>> getSteps() {
        return steps;
    }

    public Map<Integer, String> getSessionList() {
        return sessionList;
    }

    public int getOwnSessionId() {
        return ownSessionId;
    }

    public GameParams getGameParams() {
        return gameParams;
    }

    public void setGameParams(GameParams gameParams) {
        this.gameParams = gameParams;
    }

    public void setOwnSessionId(int ownSessionId) {
        this.ownSessionId = ownSessionId;
    }

    public void setSessionList(Map<Integer, String> sessionList) {
        this.sessionList = new HashMap<Integer, String>();
        this.sessionList.putAll(sessionList);
    }

    public void addStep(Step step) {
        if (steps.containsKey(step.getSequenceNumber())) {
            steps.get(step.getSequenceNumber()).add(step);
        } else {
            steps.put(step.getSequenceNumber(), new ArrayList<Step>());
            steps.get(step.getSequenceNumber()).add(step);
        }

    }
}
