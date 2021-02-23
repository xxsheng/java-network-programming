package udp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPDiscardClinet {
    public final static int PORT = 9;

    public static void main(String[] args) {
        String hostName = args.length > 0 ? args[0] : "localhost";
        try (DatagramSocket datagramSocket = new DatagramSocket()) {
            InetAddress address = InetAddress.getByName(hostName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                // 会阻塞
                String line = bufferedReader.readLine();
                if (line.equals(","))
                    break;
                byte[] data = line.getBytes();
                DatagramPacket datagramPacket = new DatagramPacket(data, data.length, address, PORT);
                datagramSocket.send(datagramPacket);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
