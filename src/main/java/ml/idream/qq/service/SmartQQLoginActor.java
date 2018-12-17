package ml.idream.qq.service;

import ml.idream.qq.common.QRCodeStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * @Description QQ登陆入口
 * @Author Aimy
 * @Date 2018/12/12 15:56
 **/
public class SmartQQLoginActor {

    private static Logger logger = LoggerFactory.getLogger(SmartQQLoginActor.class);

    /**
     * @Description 登陆入口
     * @Param []
     * @return void
     * @Author Aimy
     * @Date  
     **/
    public void doLogin() throws IOException, InterruptedException {

        CookieStore cookieStore = new BasicCookieStore();
        SSLContext context = SSLContexts.createSystemDefault();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", new PlainConnectionSocketFactory())
                .register("https", new SSLConnectionSocketFactory(context))
                .build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(cookieStore)
                .setRedirectStrategy(new LaxRedirectStrategy())
                .setConnectionManager(connectionManager).setConnectionManagerShared(true).build();

        while(true){
            Iterator<Map.Entry<String, SmartQQAccount>> iterator = SmartQQAccountList.getInstance().entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, SmartQQAccount> entry = iterator.next();
                logger.info("【{}】",entry.getKey());
                SmartQQLoginService smartQQLoginService = new SmartQQLoginService(client,entry.getValue());

                if(smartQQLoginService.checkLogin()){
                    entry.getValue().setLogin(true);

                }else{/**如果没有登陆*/
                    logger.info("【{}】没有登陆，请扫描二维码登陆！",entry.getKey());
                    QRCodeStatus flag = smartQQLoginService.checkQRCode();
                    if(flag.equals(QRCodeStatus.UNEFFECTIVITY) || flag.equals(QRCodeStatus.NONE)){
                        smartQQLoginService.getQRCode();
                        entry.getValue().setLogin(false);
                    }else{
                        if(flag.equals(QRCodeStatus.LOGIN)){
                            entry.getValue().setLogin(true);
                            String userInfo = smartQQLoginService.getUserInfo();
                            logger.info("获取到用户详细信息：【{}】",userInfo);
                        }
                    }

                }
            }
            logger.info("休眠10s...");
            /** 30s轮询一次 */
            Thread.sleep(1000 * 10);
        }

    }
}
