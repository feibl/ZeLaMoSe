/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.block.Block;
import domain.block.BlockI;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Patrick Zenhäusern
 */
public interface BlockQueueInterface {
    public Block getNextBlock();
}
