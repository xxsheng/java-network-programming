package udp;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class UDPPoke {
    private int bufferSize; //单位为字节
    private int timeout; // 单位为毫秒
    private InetAddress host;
    private int port;

    public UDPPoke(int bufferSize, int timeout, InetAddress host, int port) {
        this.bufferSize = bufferSize;
        this.timeout = timeout;
        this.host = host;
        if (port < 1 || port > 65535) {
            throw new IllegalArgumentException("Port out of range");
        }
        this.port = port;
    }

    public UDPPoke(int bufferSize, InetAddress host, int port) {
        this(bufferSize, 30000, host, port);
    }

    public UDPPoke(InetAddress host, int port) {
        this(8192, 30000, host, port);
    }

    public byte[] poke() {
        try (DatagramSocket datagramSocket = new DatagramSocket(0)) {
            DatagramPacket datagramPacket = new DatagramPacket(new byte[1], 1, host, port);
            datagramSocket.connect(host, port);
            datagramSocket.setSoTimeout(timeout);
            datagramSocket.send(datagramPacket);
            DatagramPacket incoming = new DatagramPacket(new byte[bufferSize], bufferSize);
            datagramSocket.receive(incoming);
            int length = incoming.getLength();
            byte[] response = new byte[length];
            System.arraycopy(incoming.getData(), 0, response, 0, length);
            return response;

        } catch (IOException ex) {
            return null;
        }
    }

    public static void main(String[] args) {
        InetAddress address;
        int port = 0;
        try {
            address = InetAddress.getByName(args[0]);
            port = Integer.parseInt(args[1]);
        } catch (RuntimeException | UnknownHostException ex) {
            System.out.println("Usage: java UDPPoke host port");
            return;
        }

        try {
            UDPPoke udpPoke = new UDPPoke(address, port);
            byte[] poke = udpPoke.poke();
            if (poke == null) {
                System.out.println("No response within allotted time");
                return;
            }
            String s = new String(poke, "us-ascii");
            System.out.println(s);
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }

    }
}
