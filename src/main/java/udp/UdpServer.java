package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UdpServer {

    private final static int PORT = 13;
    private final static Logger audit = Logger.getLogger("requests");
    private final static Logger errors = Logger.getLogger("errors");

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(PORT)){
            while (true) {
                try {
                    DatagramPacket datagramPacket = new DatagramPacket(new byte[1024], 1024);
                    // 此方法会阻塞
                    socket.receive(datagramPacket);
                    String dayTime = new Date().toString();
                    byte[] data = dayTime.getBytes(StandardCharsets.US_ASCII);
                    DatagramPacket response = new DatagramPacket(data, data.length, datagramPacket.getAddress(), datagramPacket.getPort());
                    socket.send(response);
                    audit.info(dayTime + " " + datagramPacket.getAddress());

                } catch (IOException | RuntimeException ex) {
                    errors.log(Level.SEVERE, ex.getMessage(), ex);
                }
            }
        } catch (IOException ex) {
            errors.log(Level.SEVERE, ex.getMessage(), ex);
        }

    }
}
