package ml.idream.qq.service;

import ml.idream.qq.entity.SmartQQAccount;
import ml.idream.qq.entity.SmartQQAccountList;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.Base64Utils;

import javax.script.ScriptException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @Description QQ登陆测试类
 * @Author Aimy
 * @Date 2018/12/17 17:34
 **/
public class SmartQQLoginActorTest {

    @Test
    public void doLogin() throws Exception {
        BasicConfigurator.configure();
        String path = SmartQQLoginActor.class.getClassLoader().getResource("").getPath();
        PropertyConfigurator.configure(path + "log4j2.yml");

        /** 测试数据*/
        SmartQQAccount account = new SmartQQAccount();
//        account.setAccount("3314287521");
        SmartQQAccountList.addSmartQQAccount(new SmartQQAccount());
//        SmartQQAccountList.addSmartQQAccount(new SmartQQAccount());
//        SmartQQAccountList.addSmartQQAccount(new SmartQQAccount());

        SmartQQLoginActor actor = new SmartQQLoginActor();
        actor.doLogin();
    }

    @Test
    public void isLogin() throws IOException {
        BasicConfigurator.configure();
        String path = SmartQQLoginActor.class.getClassLoader().getResource("").getPath();
        PropertyConfigurator.configure(path + "log4j2.yml");
        /** 测试数据*/
        SmartQQAccount account = new SmartQQAccount();

        BasicClientCookie cookie = new BasicClientCookie("qrsig","0knp*giPS5RqZbgVhIox68Yava7lyhdsBFU4Qd4w-VhK9NeOsYWxuIpN4JMsk*Eu");
        cookie.setDomain("");
        cookie.setPath("");

        CookieStore cookieStore = new BasicCookieStore();
        cookieStore.addCookie(cookie);
        account.getHttpClientContext().setCookieStore(cookieStore);
        
        SmartQQLoginActor smartQQLoginActor = new SmartQQLoginActor();
        Boolean login = smartQQLoginActor.isLogin(cookieStore);

    }

    @Test
    public void getHash() throws FileNotFoundException, ScriptException, NoSuchMethodException {
        String uin = "917708483";
        String ptfwebqq = "";
        SmartQQLoginService smartQQLoginService = new SmartQQLoginService(HttpClients.createDefault(),new SmartQQAccount());
        String hash = smartQQLoginService.getHash(uin,ptfwebqq);
        Assert.assertEquals("007300F000510088",hash);
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
        try(FileInputStream fis = new FileInputStream(image);){
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes,0,bytes.length);
            String value = Base64Utils.encodeToString(bytes);
            System.out.println(value);
        }
    }
}