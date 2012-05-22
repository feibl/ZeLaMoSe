package network;

import java.rmi.RemoteException;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class ServerFullException extends RemoteException {

    @Override
    public String getMessage() {
        return "Connection Refused: Server ist Full";
    }
}
