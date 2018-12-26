package ml.idream.qq.service;

import ml.idream.qq.common.QRCodeStatus;
import ml.idream.qq.entity.SmartQQAccount;
import ml.idream.qq.entity.SmartQQAccountList;
import net.sf.json.JSONObject;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.util.Iterator;

/**
 * @Description QQ登陆入口
 * @Author Aimy
 * @Date 2018/12/12 15:56
 **/
@Service
public class SmartQQLoginActor {

    private static Logger logger = LoggerFactory.getLogger(SmartQQLoginActor.class);

    private HttpClientBuilder clientBuilder;

    public SmartQQLoginActor() {
//        CookieStore cookieStore = new BasicCookieStore();
        SSLContext context = SSLContexts.createSystemDefault();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", new PlainConnectionSocketFactory())
                .register("https", new SSLConnectionSocketFactory(context))
                .build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        connectionManager.setMaxTotal(50);
        connectionManager.setValidateAfterInactivity(5 * 1000);
        connectionManager.setDefaultMaxPerRoute(10);
        this.clientBuilder = HttpClients.custom()
//                .setDefaultCookieStore(cookieStore)
                .setRedirectStrategy(new LaxRedirectStrategy())
                .setConnectionManager(connectionManager).setConnectionManagerShared(true);
    }

    /**
     * @Description 登陆入口
     * @Param []
     * @return void
     * @Author Aimy
     * @Date  
     **/
    public void doLogin() throws Exception {

        while(true){
            Iterator<SmartQQAccount> iterator = SmartQQAccountList.INSTANCE.getSmartQQAccountList().iterator();
            int index = 0;
            while (iterator.hasNext()) {
               SmartQQAccount entry = iterator.next();
                logger.info("【{}】", ++index);
                SmartQQLoginService smartQQLoginService = new SmartQQLoginService(clientBuilder.build(),entry);

                if(smartQQLoginService.checkLogin()){
                    entry.setLogin(true);
                    JSONObject login2 = smartQQLoginService.login2();
                    String friends = smartQQLoginService.getFriends();
                }else{/**如果没有登陆*/
                    logger.info("【{}】没有登陆，请扫描二维码登陆！",entry.getAccount());
                    QRCodeStatus flag = smartQQLoginService.checkQRCode();
                    if(flag.equals(QRCodeStatus.UNEFFECTIVITY) || flag.equals(QRCodeStatus.NONE)){
                        smartQQLoginService.getQRCode();
                        entry.setLogin(false);
                    }else{
                        if(flag.equals(QRCodeStatus.LOGIN)){
                            entry.setLogin(true);
                            JSONObject login2 = smartQQLoginService.login2();
                            logger.info("获取到用户详细信息：【{}】",login2.toString());
                            String friends = smartQQLoginService.getFriends();
                        }
                    }

                }
                smartQQLoginService.closeClient();
            }
            logger.info("休眠10s...");
            /** 30s轮询一次 */
            Thread.sleep(1000 * 10);
        }

    }

    /**
     * @Description 获取二维码
     * @Param [cookieStore]
     * @return java.lang.String
     * @Author Aimy
     * @Date  
     **/
    public String getQRCodeBase64(CookieStore cookieStore) throws IOException {
        SmartQQAccount smartQQAccount = new SmartQQAccount();
        smartQQAccount.getHttpClientContext().setCookieStore(cookieStore);
        SmartQQLoginService smartQQLoginService = new SmartQQLoginService(this.clientBuilder.build(),smartQQAccount);
        return smartQQLoginService.getQRCodeBase64();
    }
    /**
     * @Description 检查二维码状态
     * @Param [cookieStore]
     * @return java.lang.Boolean
     * @Author Aimy
     * @Date  
     **/
    public Boolean checkQRCode(CookieStore cookieStore){
        HttpClientContext context = new HttpClientContext();
        context.setCookieStore(cookieStore);
        return false;
    }
    /**
     * @Description 检查是否登陆
     * @Param [cookieStore]
     * @return java.lang.Boolean
     * @Author Aimy
     * @Date  
     **/
    public Boolean isLogin(CookieStore cookieStore) throws IOException {
        SmartQQAccount smartQQAccount = new SmartQQAccount();
        smartQQAccount.getHttpClientContext().setCookieStore(cookieStore);
        SmartQQLoginService smartQQLoginService = new SmartQQLoginService(clientBuilder.build(),smartQQAccount);
        QRCodeStatus status = smartQQLoginService.checkQRCode();
        return status.equals(QRCodeStatus.LOGIN);
    }


}
