package udp.echo;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPEchoClient {
    public final static int PORT  = 7;

    public static void main(String[] args) {
        String hostname = "localhost";
        if (args.length >0) {
            hostname = args[0];
        }

        try {
            InetAddress byName = InetAddress.getByName(hostname);
            DatagramSocket datagramSocket = new DatagramSocket();
            new Thread(new SendThread(byName, datagramSocket, PORT)).start();
            new Thread(new ReceiverThread(datagramSocket)).start();
        } catch (UnknownHostException ex) {
            System.out.println(ex);
        } catch (SocketException e) {
            System.out.println(e);
        }
    }
}
