package udp.echo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ReceiverThread implements Runnable{

    private DatagramSocket socket;
    private volatile boolean stopped = false;

    public ReceiverThread(DatagramSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        byte[] bytes = new byte[65507];
        while (true) {
            if (stopped) return;
            DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
            try {
                socket.receive(datagramPacket);
                String s = new String(datagramPacket.getData(), 0, datagramPacket.getLength(), "UTF-8");
                System.out.println(s);
                Thread.yield();
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }
}
