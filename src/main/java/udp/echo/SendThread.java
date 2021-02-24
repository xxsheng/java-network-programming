package udp.echo;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SendThread implements Runnable {
    private InetAddress inetAddress;
    private DatagramSocket datagramSocket;
    private int port;
    private volatile boolean stopped = false;

    public SendThread(InetAddress inetAddress, DatagramSocket datagramSocket, int port) {
        this.inetAddress = inetAddress;
        this.datagramSocket = datagramSocket;
        this.port = port;
    }

    public void halt() {
        this.stopped = true;
    }

    @Override
    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                if (stopped) return;
                String theLine = bufferedReader.readLine();
                if (theLine.equals(",")) break;
                byte[] data = theLine.getBytes("utf8");
                DatagramPacket datagramPacket = new DatagramPacket(data, data.length, inetAddress, port);
                datagramSocket.send(datagramPacket);
                Thread.yield();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
