package udp;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class UDPDiscardServerWithChannels {

    public final static int PORT = 9;

    public final static int MAX_PACKAGE_SIZE = 65507;

    public static void main(String[] args) {
        try {
            DatagramChannel channel = DatagramChannel.open();
            DatagramSocket socket = channel.socket();
            InetSocketAddress address = new InetSocketAddress(PORT);
            socket.bind(address);
            // 堆外内存
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(MAX_PACKAGE_SIZE);
            while (true) {
                SocketAddress receive = channel.receive(byteBuffer);
                byteBuffer.flip();
                System.out.println(receive + " says ");
                while (byteBuffer.hasRemaining())
                    System.out.println(byteBuffer.get());
                System.out.println();
                byteBuffer.clear();
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}
