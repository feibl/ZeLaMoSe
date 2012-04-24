/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.block.BlockAbstract;

/**
 *
 * @author Patrick Zenhäusern <pzenhaeu@hsr.ch>
 */
public interface BlockQueueInterface {
    public BlockAbstract getNextBlock();
}
