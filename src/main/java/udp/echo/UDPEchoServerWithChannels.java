package udp.echo;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class UDPEchoServerWithChannels {
    public final static int PORT = 7;
    public final static int MAX_PACKAGE_SIZE = 65507;

    public static void main(String[] args) {
        try {
            DatagramChannel channel = DatagramChannel.open();
            DatagramSocket socket = channel.socket();
            InetSocketAddress inetSocketAddress = new InetSocketAddress(PORT);
            socket.bind(inetSocketAddress);
            ByteBuffer buffer = ByteBuffer.allocateDirect(MAX_PACKAGE_SIZE);
            while (true) {
                SocketAddress receive = channel.receive(buffer);
                buffer.flip();
                channel.send(buffer, receive);
                buffer.clear();
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
