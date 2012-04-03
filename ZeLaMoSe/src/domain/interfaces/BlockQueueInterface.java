/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.interfaces;

import domain.block.Block;
import domain.block.IBlock;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Patrick Zenhäusern
 */
public interface BlockQueueInterface {
    public Block getNextBlock();
}
