/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;

/**
 *
 * @author Fabian Senn <fsenn@hsr.ch>
 */
public class NetworkUtil {

    public static String getLocalIP() {
        Enumeration<NetworkInterface> niList;
        try {
            niList = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface iface : Collections.list(niList)) {
                if (!iface.isLoopback() && iface.isUp()) {
                    for (InetAddress addr : Collections.list(iface.getInetAddresses())) {
                        if (addr instanceof Inet4Address) {
                            return addr.getHostAddress();
                        }
                    }
                }
            }
        } catch (SocketException ex) {
            System.out.println("Caught socket exception: " + ex.getMessage());
        }

        return "localhost";
    }
}
