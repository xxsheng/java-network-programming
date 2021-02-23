package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class FastUDPDiscardServer extends UDPServer1 {

    public final static int DEFAULT_PORT = 9;

    public FastUDPDiscardServer() {
        super(DEFAULT_PORT);
    }

    public static void main(String[] args) {
        UDPServer1 fastUDPDiscardServer = new FastUDPDiscardServer();
        Thread thread = new Thread(fastUDPDiscardServer);
        thread.start();
    }

    @Override
    public void respond(DatagramSocket socket, DatagramPacket packet) throws IOException {

    }
}
