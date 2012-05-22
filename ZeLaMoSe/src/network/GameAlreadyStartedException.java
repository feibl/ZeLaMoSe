package network;

import java.rmi.RemoteException;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class GameAlreadyStartedException extends RemoteException {

    @Override
    public String getMessage() {
        return "Connection Refused: Game already started";
    }
}
