/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jogl2;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Cyrill
 */
public class BlockStack {
    
    private ArrayList<TestBlock> blockList = new ArrayList<TestBlock>();
    private ArrayList<Integer> xPosList = new ArrayList<Integer>();
    private ArrayList<Integer> yPosList = new ArrayList<Integer>();
    
    public void add(TestBlock block, int posx, int posy){
        blockList.add(block);
        xPosList.add(posx);
        yPosList.add(posy);
    }
    
    public TestBlock getBlock(int i){
        return blockList.get(i);
    }
    public int getXpos(int i){
        return xPosList.get(i);
    }
    
    public int getypos(int i){
        return yPosList.get(i);
    }
    
    public int getSize(){
        return blockList.size();
    }
    
}
