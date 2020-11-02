package thread.callback;

import javax.xml.bind.DatatypeConverter;

public class CallbackDigestUserInterface {

    public static void receiveDigest(byte[] digest, String name) {
        StringBuilder stringBuilder = new StringBuilder(name);
        stringBuilder.append(": ");
        stringBuilder.append(DatatypeConverter.printHexBinary(digest));
        System.out.println(stringBuilder);
    }

    public static void main(String[] args) {
        for (String arg : args) {
            // 计算摘要
            CallbackDigest callbackDigest = new CallbackDigest(arg);
            new Thread(callbackDigest).start();
        }
    }
}
