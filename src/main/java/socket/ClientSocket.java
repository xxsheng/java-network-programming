package socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;

public class ClientSocket {

    public static final int DEFAULT_PORT = 19;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java ChargeClient host [port]");
            return;
        }

        int port;
        try {
            port = Integer.parseInt(args[1]);
        } catch (RuntimeException ex) {
            port = DEFAULT_PORT;
        }

        try {
            InetSocketAddress inetSocketAddress = new InetSocketAddress(args[0], port);
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.bind(inetSocketAddress);

            ByteBuffer allocate = ByteBuffer.allocate(74);
            WritableByteChannel writableByteChannel = Channels.newChannel(System.out);

            while (socketChannel.read(allocate) != -1) {
                allocate.flip();

                writableByteChannel.write(allocate);
                allocate.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
