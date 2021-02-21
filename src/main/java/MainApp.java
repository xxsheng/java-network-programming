
import sun.misc.BASE64Decoder;

import java.nio.charset.Charset;
import java.util.Base64;

public class MainApp {
    private static final String  SKEY    = "IKWLgczsuSwpDhKjeH8YLa4Bd7EU5s35";
    private static final Charset CHARSET = Charset.forName("utf8");

    public static void main(String[] args) throws Exception {
//        // 待加密内容
        String str = "330ac00d58764bf3a1aac212201bd5f6";
        String encryptResult = DesUtil.encrypt(str, CHARSET, SKEY);
        System.out.println(encryptResult);
        // 直接将如上内容解密
        String decryResult = "";
        try {
            decryResult = DesUtil.decrypt(encryptResult, CHARSET, SKEY);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        System.out.println(decryResult);
//        Des des = new Des();
//        String s = des.encryptDESCBC("00000000000000000000000000000002", "IKWLgczsuSwpDhKjeH8YLa4Bd7EU5s35");
//        System.out.println(s);
//        System.out.println(des.decryptDESCBC("2nZgQlpY0Vap2QClIzKUeQCn+Bcj75Q7VbG+s1uWYGjlDP++cUzKzw==", "IKWLgczsuSwpDhKjeH8YLa4Bd7EU5s35"));
//        byte[] decode = Base64.getDecoder().decode("U2FsdGVkX1+/uRMmdblK48d09eNnFyRfpdZ7R+5aZHGQTcuM0wKfvMbm3WhIz+OUZxebVu4bGLY=");
//        System.out.println(Des.parseByte2HexStr(decode));
    }
}
