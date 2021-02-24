package udp.echo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

public class UDPEchoClientWithChannels {

    public final static int PORT = 7;
    private final static int LIMIT = 100;

    public static void main(String[] args) {
        SocketAddress socketAddress;
        try {
            socketAddress = new InetSocketAddress(args[0], PORT);
        } catch (RuntimeException ex) {
            System.err.println("Usage: java UDPEchoClientWithChannels host");
            return;
        }

        try (DatagramChannel channel = DatagramChannel.open()){
            channel.configureBlocking(false);
            channel.connect(socketAddress);

            Selector selector = Selector.open();
            channel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

            ByteBuffer buffer = ByteBuffer.allocate(4);
            int n = 0;
            int numbersRead = 0;
            while (true) {
                if (numbersRead == LIMIT) {
                    break;
                }
                selector.select(60000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                if (selectionKeys.isEmpty() && n == LIMIT) {
                    // 已全部发送完毕
                    break;
                } else {
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();
                        iterator.remove();
                        if (selectionKey.isReadable()) {
                            buffer.clear();
                            channel.read(buffer);
                            buffer.flip();
                            int anInt = buffer.getInt();
                            System.out.println("Read: " + anInt);
                            numbersRead ++;
                        } else if (selectionKey.isWritable()) {
                            buffer.clear();
                            buffer.putInt(n);
                            buffer.flip();
                            channel.write(buffer);
                            System.out.println("Wrote: " + n);
                            n ++;
                            if (n == LIMIT) {
                                selectionKey.interestOps(SelectionKey.OP_READ);
                            }
                        }
                    }
                }
            }
        } catch (IOException ex) {

        }
    }
}
