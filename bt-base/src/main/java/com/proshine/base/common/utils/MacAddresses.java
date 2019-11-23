package com.proshine.base.common.utils;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

public class MacAddresses {
    private static String[] ETHERNET_NAMES = {"eth"};
    private static LinkedList<Long> macs = null;
    static {
        macs = new LinkedList<>();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();
                byte[] mac = networkInterface.getHardwareAddress();
                if (mac != null && mac.length == 6) {
                    long value = 0;
                    for (int i = 0; i < mac.length; i++) {
                        value = (value << 8) + (mac[i] & 0xff);
                    }
                    String name = networkInterface.getName();
                    boolean isEthernet = false;
                    for (String ethernetName : ETHERNET_NAMES) {
                        // Add ethernet at the first.
                        if (name.contains(ethernetName)) {
                            isEthernet = true;
                            macs.addFirst(new Long(value));
                            break;
                        }
                    }
                    if (!isEthernet) {
                        // Add non-ethernet at the end.
                        macs.addLast(new Long(value));
                    }
                }
            }
        } catch (SocketException e) {
        }
    }
    public static List<Long> getMacs() {
        return macs;
    }
}
