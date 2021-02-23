package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UdpTest {
    public static void main(String[] args) {
        try (DatagramSocket datagramSocket = new DatagramSocket(0)) {
            datagramSocket.getLocalPort();
//            datagramSocket.setSoTimeout(10000);
            InetAddress inetAddress = InetAddress.getByName("localhost");
            DatagramPacket request = new DatagramPacket(new byte[1], 1, inetAddress, 19);
            byte[] data = new byte[1024];
            DatagramPacket response = new DatagramPacket(data, data.length);
            datagramSocket.send(request);
            // 此方法会阻塞
            datagramSocket.receive(response);
            String result = new String(response.getData(), 0, response.getLength(), "US-ASCII");
            System.out.println(result);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
