package ml.idream.encrypt;

import sun.security.krb5.internal.crypto.Des;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

public class AESUtils {

    private static String privateKey = UUID.randomUUID().toString().replace("-","");

    private static KeyGenerator aesGenerator;
    static{
        try {
            aesGenerator = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /*
    * 加密
    * */
    public static String encrypt(String passWord) throws NoSuchAlgorithmException, NoSuchPaddingException {
        aesGenerator.init(128,new SecureRandom(passWord.getBytes()));
        SecretKey secretKey = aesGenerator.generateKey();
        byte[] enCondeFormat = secretKey.getEncoded();
        SecretKeySpec secretKeySpec = new SecretKeySpec(enCondeFormat,"AES");
        Cipher cipher =  Cipher.getInstance("AES");
        
        return "";
    }

    /*
    * 解密
    * */
    public static String decode(String passWord){

        return "";
    }

}
