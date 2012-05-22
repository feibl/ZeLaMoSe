package network.client;

import domain.Config;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Observable;
import javax.swing.DefaultListModel;

/**
 *
 * @author Cyrill Lam <clam@hsr.ch>
 */
public class DiscoveryClient extends Observable {

    final int receiveDuration = 5000;

    public enum DiscoveryState {

        STARTED, FINISHED
    }

    public DefaultListModel getServerListModel() {
        setChanged();
        notifyObservers(DiscoveryState.STARTED);
        final DefaultListModel listModel = new DefaultListModel();
        try {
            final MulticastSocket socket = new MulticastSocket(Config.discoveryPort);
            InetAddress group = InetAddress.getByName(Config.discoveryMultiCastGroup);
            socket.joinGroup(group);
            socket.setSoTimeout(receiveDuration);

            byte[] discoveryMessage = Config.discoveryClientMessage.getBytes();
            socket.send(new DatagramPacket(discoveryMessage, discoveryMessage.length, group, Config.discoveryPort));
            Thread.yield();
//        In case first packet got lost
            socket.send(new DatagramPacket(discoveryMessage, discoveryMessage.length, group, Config.discoveryPort));


            new Thread(new Runnable() {

                @Override
                public void run() {

                    long discoveryStarted = System.currentTimeMillis();
                    while (System.currentTimeMillis() - discoveryStarted <= receiveDuration) {
                        try {
                            byte[] receiveBuffer = new byte[256];
                            DatagramPacket recievePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                            socket.receive(recievePacket);
                            String hostAddress = recievePacket.getAddress().getHostAddress();

                            if (new String(receiveBuffer, 0, recievePacket.getLength()).equals(Config.discoveryServerMessage)
                                    && !listModel.contains(hostAddress)) {
                                listModel.addElement(hostAddress);
                            }
                        } catch (Exception e) {
                        }
                    }
                    setChanged();
                    notifyObservers(DiscoveryState.FINISHED);

                }
            }).start();
        } catch (IOException e) {
        }

        return listModel;
    }
}
