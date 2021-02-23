package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPDiscardServer {

    public static final int PORT = 9;
    public static int MAX_PACKAGE_SIZE = 65507;

    public static void main(String[] args) {

        byte[] buffer = new byte[MAX_PACKAGE_SIZE];
        try (DatagramSocket datagramSocket = new DatagramSocket(PORT)) {
            DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
            while (true) {
                try {
                    datagramSocket.receive(datagramPacket);
                    String s = new String(datagramPacket.getData(), 0, datagramPacket.getLength(), "8859_1");
                    System.out.println(datagramPacket.getAddress() + "at port " + datagramPacket.getPort() + "says " + s);

                    // 重置下一个数据包的长度
                    datagramPacket.setLength(buffer.length);
                } catch (IOException ex) {
                    System.err.println(ex);
                }
            }
        }catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
