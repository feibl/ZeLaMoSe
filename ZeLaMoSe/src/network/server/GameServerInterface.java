package network.server;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public interface GameServerInterface {
    public void startGame(long blockQueueSeed, int nbrOfJokers, boolean includeSpecialBlocks, int startLevel);
    public void startDiscoveryServer();
    public void stopDiscoveryServer();
}
