package ml.idream.encrypt;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.Base64Utils;

import javax.crypto.*;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AESUtils {

//    private static String privateKey = UUID.randomUUID().toString().replace("-","");
    private static String privateKey = "21a160c3ef1a46e29821a48dacca2bde";
    private static SecretKey secretKey;
    private static KeyGenerator aesGenerator;
    private static Cipher cipher;
    static{
        try {
            aesGenerator = KeyGenerator.getInstance("AES");
            aesGenerator.init(128,new SecureRandom(privateKey.getBytes()));
            secretKey = aesGenerator.generateKey();
            cipher =  Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    /*
    * 加密
    * */
    public static String encrypt(String passWord) throws InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.ENCRYPT_MODE,secretKey);
        return Base64Utils.encodeToUrlSafeString(cipher.doFinal(passWord.getBytes("UTF-8")));
    }

    /*
    * 解密
    * */
    public static String decode(String passWord) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.DECRYPT_MODE,secretKey);
        byte[] deCode = cipher.doFinal(Base64Utils.decodeFromUrlSafeString(passWord));
        return new String(deCode);
    }

    @Test
    public void testAES() throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, UnsupportedEncodingException {
        String passWord = "123123";
        String enCode = AESUtils.encrypt(passWord);
        System.out.println(enCode);
        String deCode = AESUtils.decode(enCode);
        Assert.assertEquals("加密解密失败!",passWord,deCode);
    }
}
