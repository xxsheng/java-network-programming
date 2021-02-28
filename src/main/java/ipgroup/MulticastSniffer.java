package ipgroup;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class MulticastSniffer {
    public static void main(String[] args) {
        InetAddress group = null;
        int port = 0;

        // 从命令行读取地址
        try {
            group = InetAddress.getByName(args[0]);
            port = Integer.parseInt(args[1]);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException | UnknownHostException ex)   {
            System.err.println("Usage: java MulticastSniffer multicast_address post");
            System.exit(1);
        }

        MulticastSocket ms = null;
        try {
            ms = new MulticastSocket(port);
            ms.joinGroup(group);
            byte[] bytes = new byte[8192];

            while (true) {
                DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
                ms.receive(datagramPacket);
                String result = new String(datagramPacket.getData(), "8859_1");
                System.out.println(result);
            }

        } catch (IOException ex) {
            System.err.println(ex);
        } finally {
            if (ms != null) {
                try {
                    ms.leaveGroup(group);
                    ms.close();
                } catch (IOException ex) {}
            }
        }
    }
}
