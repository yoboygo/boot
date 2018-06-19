package ml.idream.encrypt;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.Base64Utils;
import sun.security.krb5.internal.crypto.Des;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

public class AESUtils {

    private static String privateKey = UUID.randomUUID().toString().replace("-","");
    private static Cipher cipher;
    private static SecretKeySpec secretKeySpec;
    private static KeyGenerator aesGenerator;
    private static SecretKey secretKey;

    static{
        try {
            aesGenerator = KeyGenerator.getInstance("AES");
            aesGenerator.init(128,new SecureRandom(privateKey.getBytes()));//密钥
            secretKey = aesGenerator.generateKey();
            byte[] enCondeFormat = secretKey.getEncoded();//生成密钥数组
            secretKeySpec = new SecretKeySpec(enCondeFormat,"AES");
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
    public static String encrypt(String passWord) throws UnsupportedEncodingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.ENCRYPT_MODE,secretKey);
        byte[] encode = cipher.doFinal(passWord.getBytes("UTF-8"));
        return Base64Utils.encodeToString(encode);
    }

    /*
    * 解密
    * */
    public static String decode(String passWord) throws UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException {
        cipher.init(Cipher.DECRYPT_MODE,secretKey);
        return new String(Base64Utils.decode(cipher.doFinal(passWord.getBytes("UTF-8"))));
    }

    @Test
    public void testAES() throws Exception {

        String passWord = "12345";
        String enCode = AESUtils.encrypt(passWord);
        String deCode = AESUtils.decode(enCode);

        System.out.println("加密之后：" + enCode);
        System.out.println("解密之后：" + deCode);

        Assert.assertEquals("加密解密之后的内容不符！",passWord,deCode);
    }

}
