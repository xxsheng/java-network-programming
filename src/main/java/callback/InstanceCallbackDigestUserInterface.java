package callback;

import javax.xml.bind.DatatypeConverter;

public class InstanceCallbackDigestUserInterface {
    private String filename;
    private byte[] digest;

    public InstanceCallbackDigestUserInterface(String filename) {
        this.filename = filename;
    }

    public void calculateDigest() {
        InstanceCallbackDigest instanceCallbackDigest = new InstanceCallbackDigest(filename, this);
        new Thread(instanceCallbackDigest).start();
    }

    void receiveDigest(byte[] digest) {
        this.digest = digest;
        System.out.println(this);
    }

    @Override
    public String toString() {
        String result = filename + ": ";
        if (digest != null) {
            result += DatatypeConverter.printHexBinary(digest);
        } else {
            result += "digest not available";
        }
        return result;
    }

    public static void main(String[] args) {
        for (String arg : args) {
            InstanceCallbackDigestUserInterface instanceCallbackDigestUserInterface = new InstanceCallbackDigestUserInterface(arg);
            instanceCallbackDigestUserInterface.calculateDigest();
        }
    }
}
