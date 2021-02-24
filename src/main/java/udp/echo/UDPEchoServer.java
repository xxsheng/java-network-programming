package udp.echo;

import udp.UDPServer1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPEchoServer extends UDPServer1 {

    public final static int DEFAULT_PORT = 7;

    public UDPEchoServer() {
        super(DEFAULT_PORT);
    }

    @Override
    public void respond(DatagramSocket socket, DatagramPacket packet) throws IOException {
        DatagramPacket datagramPacket = new DatagramPacket(packet.getData(), packet.getLength(), packet.getAddress(), packet.getPort());
        socket.send(datagramPacket);
    }

    public static void main(String[] args) {
        UDPEchoServer udpEchoServer = new UDPEchoServer();
        new Thread(udpEchoServer).start();
    }
}
