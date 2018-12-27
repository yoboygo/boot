package ml.idream.qq.service;

import ml.idream.qq.common.QRCodeStatus;
import ml.idream.qq.entity.SmartQQAccount;
import ml.idream.qq.entity.SmartQQAccountList;
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
    public void doLogin(String loginKey) throws Exception {

        while(true){
            Iterator<SmartQQAccount> iterator = SmartQQAccountList.INSTANCE.getSmartQQAccount(loginKey).iterator();
            int index = 0;
            while (iterator.hasNext()) {

                SmartQQAccount entry = iterator.next();
                ++index;
                logger.info("【{}】", index);
                SmartQQLoginService smartQQLoginService = new SmartQQLoginService(clientBuilder.build(),entry);

                try {
                    //重试次数+1
                    entry.tryTimeInc();
                    //登陆成功后只获取好友列表
                    if(smartQQLoginService.checkLogin()){
                        smartQQLoginService.getFriends();
                    }else{

                        QRCodeStatus status = smartQQLoginService.checkQRCode();
                        entry.setStatus(status);
                        switch (status){
                            case NONE:   /**如果没有登陆或者二维码失效，则重新获取二维码*/
                            case UNEFFECTIVITY:
                                smartQQLoginService.getQRCode();
                                break;
                            case LOGIN:   /**登陆成功之后调用affterLogin方法，获取必要的参数*/
                                smartQQLoginService.affterLogin();
                                logger.info("【{}】登陆成功！",smartQQLoginService.getSmartQQAccount().getAccount());
                                break;
                        }

                    }
                }catch (Exception e){
                    logger.error("账号检查失败！",e);
                }finally {
                    smartQQLoginService.closeClient();
                }

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
    public String getQRCodeBase64(CookieStore cookieStore) throws Exception {
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
    public SmartQQAccount isLogin(CookieStore cookieStore) throws Exception {
        SmartQQAccount smartQQAccount = new SmartQQAccount();
        smartQQAccount.getHttpClientContext().setCookieStore(cookieStore);
        SmartQQLoginService smartQQLoginService = new SmartQQLoginService(clientBuilder.build(),smartQQAccount);
        QRCodeStatus status = smartQQLoginService.checkQRCode();
        smartQQAccount.setStatus(status);
        return smartQQAccount;
    }


}
