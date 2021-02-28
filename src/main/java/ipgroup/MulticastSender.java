package ipgroup;


import java.io.IOException;
import java.net.*;

public class MulticastSender {

    public static void main(String[] args) {
        InetAddress ia = null;
        int port = 0;
        byte ttl = (byte) 1;

        // 从命令行读取数据
        try {
            ia = InetAddress.getByName(args[0]);
            port = Integer.parseInt(args[1]);

            if (args.length >2) ttl = (byte)Integer.parseInt(args[2]);

        } catch (NumberFormatException | IndexOutOfBoundsException | UnknownHostException ex) {
            System.err.println(ex);
            System.err.println("Usage: java MulticastSender multicast_adress port ttl");
            System.err.println(1);
        }

        byte[] data = "Here's some multicast data \r\n".getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(data, data.length, ia, port);
        try (MulticastSocket multicastSocket = new MulticastSocket()){
            multicastSocket.setTimeToLive(ttl);
            multicastSocket.joinGroup(ia);
            for (int i = 0; i <10; i++) {
                multicastSocket.send(datagramPacket);
            }
            multicastSocket.leaveGroup(ia);
        } catch (SocketException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
