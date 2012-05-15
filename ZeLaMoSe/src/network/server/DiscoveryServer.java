package network.server;

import domain.Config;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 *
 * @author Patrick Zenhäusern
 */
public class DiscoveryServer extends Thread {
    private boolean doDiscovery = true;
    
    @Override
    public void run() {
        try {
            MulticastSocket socket = new MulticastSocket(Config.discoveryPort);
            InetAddress group = InetAddress.getByName(Config.discoveryMultiCastGroup);
            socket.joinGroup(group);
            while (doDiscovery) {
                byte[] buffer = new byte[1024];
                DatagramPacket input = new DatagramPacket(buffer, buffer.length);
                socket.receive(input);
                System.out.println(new String(buffer, 0, input.getLength()));
                if ((new String(buffer, 0, input.getLength())).equals(Config.discoveryClientMessage) && doDiscovery) {
                    byte[] data = Config.discoveryServerMessage.getBytes();
                    DatagramPacket request = new DatagramPacket(data, data.length, input.getAddress(), input.getPort());
                    socket.send(request);
                }

            }
        } catch (Exception e) {
            System.out.println("DiscoveryServer had an error: " + e.getMessage());
        }
    }

    public void end() {
        doDiscovery = false;
    }
}
