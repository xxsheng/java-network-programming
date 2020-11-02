package thread.callback;

import java.io.FileInputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;

public class InstanceCallbackDigest implements Runnable {

    private String filename;
    private InstanceCallbackDigestUserInterface instanceCallbackDigestUserInterface;

    public InstanceCallbackDigest(String filename, InstanceCallbackDigestUserInterface instanceCallbackDigestUserInterface) {
        this.filename = filename;
        this.instanceCallbackDigestUserInterface = instanceCallbackDigestUserInterface;
    }

    @Override
    public void run() {
        try {
            FileInputStream fileInputStream = new FileInputStream(filename);
            MessageDigest instance = MessageDigest.getInstance("SHA-256");
            DigestInputStream digestInputStream = new DigestInputStream(fileInputStream, instance);
            while (digestInputStream.read() != -1) ; // 读取整个文件
            digestInputStream.close();
            byte[] digest = instance.digest();

        } catch (Exception e) {

        }
    }
}
