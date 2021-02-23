package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class UDPServer1 implements Runnable{

    private final int buffersize;
    private final int port;
    private final Logger logger = Logger.getLogger(UDPServer1.class.getCanonicalName());
    private volatile boolean isShutDown = false;

    public UDPServer1(int port, int buffersize) {
        this.port = port;
        this.buffersize = buffersize;
    }

    public UDPServer1(int port) {
        this(port, 8192);
    }

    @Override
    public void run() {

        byte[] bytes = new byte[buffersize];
        try (DatagramSocket datagramSocket = new DatagramSocket(port)) {
            datagramSocket.setSoTimeout(10000);
            while (true) {
                if (isShutDown) return;
                DatagramPacket datagramPacket = new DatagramPacket(bytes, buffersize);
                try {
                    datagramSocket.receive(datagramPacket);
                    this.respond(datagramSocket, datagramPacket);
                } catch (IOException ex) {
                    logger.log(Level.WARNING, ex.getMessage(), ex);
                }
            } // 结束while
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Could not bind to port: " + port, ex);
        }
    }

    public abstract void respond(DatagramSocket socket, DatagramPacket packet) throws IOException;

    public void shutDown() {
        this.isShutDown = true;
    }
}

class A {

}