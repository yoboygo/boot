package ml.idream.qq.service;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;
import org.springframework.util.Base64Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @Description QQ登陆测试类
 * @Author Aimy
 * @Date 2018/12/17 17:34
 **/
public class SmartQQLoginActorTest {

    @Test
    public void doLogin() throws IOException, InterruptedException {
        BasicConfigurator.configure();
        String path = SmartQQLoginActor.class.getClassLoader().getResource("").getPath();
        PropertyConfigurator.configure(path + "/log4j2.yml");

        /** 测试数据*/
        SmartQQAccount account = new SmartQQAccount();
        account.setAccount("3314287521");
        SmartQQAccountList.setSmartQQAccount(account);

        SmartQQLoginActor actor = new SmartQQLoginActor();
        actor.doLogin();
    }

    /**
     * @Description 图片Base64编码
     * @Param []
     * @return void
     * @Author Aimy
     * @Date  
     **/
    @Test
    public void pngBase64() throws IOException {
        String path = "D:\\QRCode\\3314287521_1545038162703.png";
        File image = new File(path);
        FileInputStream fis = new FileInputStream(image);
        byte[] bytes = new byte[fis.available()];
        fis.read(bytes,0,bytes.length);
        String value = Base64Utils.encodeToString(bytes);
        System.out.println(value);
    }
}