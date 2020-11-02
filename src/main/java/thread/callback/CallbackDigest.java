package thread.callback;

import java.io.FileInputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;

public class CallbackDigest implements Runnable {

    private String filename;

    public CallbackDigest(String filename) {
        this.filename = filename;
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
