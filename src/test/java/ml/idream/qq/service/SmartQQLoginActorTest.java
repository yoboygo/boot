package ml.idream.qq.service;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

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
}