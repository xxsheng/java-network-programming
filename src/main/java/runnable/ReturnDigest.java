package runnable;

import org.junit.Test;
import thread.DigestThread;

import javax.xml.bind.DatatypeConverter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ReturnDigest extends Thread{
    private String filename;

    public byte[] getDigest() {
        return digest;
    }

    private byte[] digest;

    public ReturnDigest(String filename) {
        this.filename = filename;
    }

    @Override
    public void run() {
        try(FileInputStream in = new FileInputStream(filename)) {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            DigestInputStream digestInputStream = new DigestInputStream(in, sha);
            while (digestInputStream.read() != -1);
            digestInputStream.close();
            StringBuilder stringBuilder = new StringBuilder(filename);
            stringBuilder.append(": ");
            digest = sha.digest();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void main(String[] args) {
        ReturnDigest[] returnDigests = new ReturnDigest[args.length];
        for (int i = 0; i < args.length; i++) {
            // 计算摘要
            returnDigests[i] = new ReturnDigest(args[0]);
            returnDigests[i].start();
        }

        for (int i = 0; i < args.length; i++) {
            StringBuffer append = new StringBuffer(args[i]).append(": ")
                    .append(DatatypeConverter.printHexBinary(returnDigests[i].getDigest()));
            System.out.println(append);
        }
    }

    /**
     * 轮询
     * @param args
     */
    @Test
    public void main1(String[] args) {
        ReturnDigest[] returnDigests = new ReturnDigest[args.length];
        for (int i = 0; i < args.length; i++) {
            // 计算摘要
            returnDigests[i] = new ReturnDigest(args[0]);
            returnDigests[i].start();
        }

        for (int i = 0; i < args.length; i++) {
            while (true) {
                // 现在显示结果
                byte[] digest = returnDigests[i].getDigest();
                if (digest != null) {
                    StringBuffer append = new StringBuffer(args[i]).append(": ")
                            .append(DatatypeConverter.printHexBinary(returnDigests[i].getDigest()));
                    System.out.println(append);
                    break;
                }
            }
        }
    }

}
