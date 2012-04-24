/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.fake;

import domain.interfaces.BlockQueueInterface;
import domain.block.Block;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Patrick Zenhäusern
 */
public class FakeBlockQueue  implements BlockQueueInterface {
        public Queue<Block> blocklist = new LinkedList <Block>();
 
        @Override
        public Block getNextBlock(){
            return blocklist.remove();
    }
}
