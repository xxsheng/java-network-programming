package thread;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DigestThread extends Thread{
    private String filename;

    public DigestThread(String filename) {
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
            byte[] digest = sha.digest();
            stringBuilder.append(DatatypeConverter.printHexBinary(digest));
            System.out.println(stringBuilder);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        for (String arg : args) {

            DigestThread digestThread = new DigestThread(arg);
            digestThread.start();
        }
    }
}
