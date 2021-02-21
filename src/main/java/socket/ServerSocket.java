package socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketOption;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ServerSocket {

    public static int DEFAULT_PORT = 19;

    public static void main(String[] args) {
        int port;
        try {
            port = Integer.parseInt(args[0]);
        } catch (RuntimeException ex) {
            port = DEFAULT_PORT;
        }

        System.out.println("Listening for connecting on port " + port);
        byte[] rotation = new byte[95 * 2];
        for (byte i= ' '; i <= '~'; i++) {
            rotation[i - ' '] = i;
            rotation[i + 95 - ' '] = i;
        }

        ServerSocketChannel serverSocketChannel;
        Selector selector;

        try {
            serverSocketChannel = ServerSocketChannel.open();
            java.net.ServerSocket socket = serverSocketChannel.socket();
            InetSocketAddress inetSocketAddress = new InetSocketAddress(port);
            socket.bind(inetSocketAddress);
            serverSocketChannel.configureBlocking(false);

            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        while (true) {
            try {
                selector.select();
            } catch (IOException ex) {
                ex.printStackTrace();
                return;
            }

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                iterator.remove();

                try {
                    if (next.isAcceptable()) {
                        ServerSocketChannel channel = (ServerSocketChannel) next.channel();
                        SocketChannel accept = channel.accept();
                        System.out.println("ACCEPT CONNECTION FROM " + accept);
                        accept.configureBlocking(false);
                        SelectionKey selectionKey = accept.register(selector, SelectionKey.OP_WRITE);
                        ByteBuffer allocate = ByteBuffer.allocate(74);
                        allocate.put(rotation, 0, 72);
                        allocate.put((byte) '\r');
                        allocate.put((byte) '\n');
                        allocate.flip();
                        selectionKey.attach(allocate);
                    } else if (next.isWritable()) {
                        SocketChannel channel = (SocketChannel) next.channel();
                        ByteBuffer attachment = (ByteBuffer) next.attachment();
                        if (!attachment.hasRemaining()) {
                            // 用于下一行重新填充缓冲区
                            attachment.rewind();
                            // 得到上一次得首字符
                            byte b = attachment.get();
                            // 准备改变缓冲区中得数据
                            attachment.rewind();
                            // 寻找rotation中新的首字符位置
                            int position = b - ' ' + 1;
                            // 将数据从rotation复制到缓冲区
                            attachment.put(rotation, position, 72);
                            // 在缓冲区末尾存储一个行分隔符
                            attachment.put((byte) '\r');
                            attachment.put((byte) '\n');
                            // 准备缓冲区中进行写入
                            attachment.flip();
                        }

                        channel.write(attachment);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    next.cancel();
                    try {
                        next.channel().close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
