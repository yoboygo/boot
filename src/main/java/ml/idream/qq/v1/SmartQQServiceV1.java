package ml.idream.qq.v1;

import ml.idream.qq.entity.StringResponseBody;
import ml.idream.qq.handler.ImageResponseHander;
import ml.idream.qq.handler.StringResponseHandler;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.HTMLLayout;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.xml.ws.spi.http.HttpContext;
import java.io.IOException;
import java.util.Calendar;

/**
 * @Description SmartQQ业务类
 * <p>
 * 记录所有的cookies
 * 1、获取二维码，将二维码输出到console
 * 2、定时轮询是否扫码
 * 3、如果扫码，跳转到指定连接
 * 4、调用用户接口
 * 参考：http://hc.apache.org/httpclient-3.x/methods.html
 * @Author Aimy
 * @Date 2018/12/5 10:23
 **/
public class SmartQQServiceV1 extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(SmartQQServiceV1.class);
    //请求的上下文环境
    private SSLContext context;
    //全局客户端
    private CloseableHttpClient globleClient;

    /**cookieStore*/
    private CookieStore cookieStore;

    public SmartQQServiceV1() {
        this.context = SSLContexts.createSystemDefault();
        this.cookieStore = new BasicCookieStore();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", new PlainConnectionSocketFactory())
                .register("https", new SSLConnectionSocketFactory(context))
                .build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        this.globleClient = HttpClients.custom().setDefaultCookieStore(cookieStore)
                .setRedirectStrategy(new LaxRedirectStrategy())
                .setConnectionManager(connectionManager).setConnectionManagerShared(true).build();
    }

    /**
     * @Description 获取本地登陆的LoginSig
     * @Param []
     * @return void
     * @Author AimyAimy
     * @Date  
     **/
    public void getLoginSig() throws IOException {
        logger.info("准备获取【LoginSig】...");
        HttpGet method = new HttpGet(SmartQQConfigV1.URL_LOGIN_SIG);
        getGlobleClient().execute(method,new StringResponseHandler());
        logger.info("获取【LoginSig】成功！");
    }

    /**
     * @Description 将qrsig转换成token
     * @Param [qrsig]
     * @return int
     * @Author Aimy
     * @Date  
     **/
    public int getQrpttoken(String qrsig){
        int e = 0,i,n = qrsig.length();
        for(i = 0;i < n; ++i){
            e += (e << 5) + qrsig.charAt(i);
        }
        return 2147483647 & e;
    }

    /**
     * @return void
     * @Description 获取二维码打印到consoles
     * @Param
     * @Author Aimy
     * @Date
     **/
    public void getEwm() throws IOException {
        logger.info("开始拉取二维码...");
        HttpGet method = new HttpGet(SmartQQConfigV1.URL_EWM);
        getGlobleClient().execute(method, new ImageResponseHander("d:\\ewm"));
//        client.execute(method);
        logger.info("拉取二维码完成!");

    }


    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        String path = SmartQQServiceV1.class.getClassLoader().getResource("").getPath();
        PropertyConfigurator.configure(path + "/log4j2.yml");

        SmartQQServiceV1 qqLogin = new SmartQQServiceV1();
        qqLogin.start();

    }


    /**
     * @return void
     * @Description 1、获取二维码，存放到指定目录
     * 2、每隔1s轮询二维码是否过期，如果过期重新拉取，如果有人扫码，跳转到指定页面，然后请求个人信息
     * @Param []
     * @Author Aimy
     * @Date
     **/
    @Override
    public void run() {
        try {

//            getLoginSig();
            //获取二维码
            getEwm();
            //是否有人扫过码
            while (!checkScand()) {
                Thread.currentThread().sleep(1000);
            }
            //获取个人信息
            getUserInfo();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return void
     * @Description 获取个人信息
     * @Param []
     * @Author Aimy
     * @Date
     **/
    private void getUserInfo() throws IOException {
        logger.info("登陆成功，获取个人信息...");
        HttpGet methodGet = new HttpGet(SmartQQConfigV1.URL_GET_USERINFO + "?t=" + Calendar.getInstance().getTimeInMillis());
        methodGet.setHeader("referer","https://d1.web2.qq.com/proxy.html?v=20151105001&callback=1&id=2");
        StringResponseBody result = getGlobleClient().execute(methodGet,new StringResponseHandler());
        logger.info("获取到的个人信息为：【{}】",JSONObject.fromObject(result));
    }

    /**
     * @Description 获取vfwebqq
     * @Param []
     * @return java.lang.String
     * @Author AimyAimy
     * @Date  
     **/
    private String getVFWebQQ() throws IOException {
        logger.info("获取VFWebQQ...");
        HttpGet methodGet = new HttpGet(SmartQQConfigV1.URL_GET_VFWEBQQ);
        methodGet.setHeader("referer","https://s.web2.qq.com/proxy.html?v=20130916001&callback=1&id=1");
        StringResponseBody result = getGlobleClient().execute(methodGet,new StringResponseHandler());
        JSONObject vfwebqq = JSONObject.fromObject(result);
        logger.info("获取到的VFWebQQ信息为：【{}】",vfwebqq);
        if(0 == vfwebqq.getInt("retcode")){
            return vfwebqq.getJSONObject("result").getString("vfwebqq");
        }else {
            logger.error("获取vfwebqq失败！");
        }
        return "";
    }

    /**
     * @return
     * @Description 检查二维码是否被扫过
     * @Param
     * @Author Aimy
     * @Date
     **/
    private boolean checkScand() throws IOException {
        logger.info("检查二维码是否过期...");
        HttpGet getMethod = new HttpGet(getCheckLoginUrl());
        RequestConfig requestConfig = RequestConfig.custom().setRedirectsEnabled(true).setCircularRedirectsAllowed(true).build();
        getMethod.setConfig(requestConfig);
        StringResponseBody loginCheckResponse = getGlobleClient().execute(getMethod, new StringResponseHandler());
        logger.info("【{}】", loginCheckResponse);
        if(SmartQQConfigV1.isLagel(loginCheckResponse.getValue())){/**二维码失效，重新获取二维码 */
            getEwm();
            return false;
        }
        String _url = SmartQQConfigV1.scanSuccessUrl(loginCheckResponse.getValue());
        if (StringUtils.isNotBlank(_url)) {//扫码失败返回的_url是""
            logger.info("扫码成功，请求check地址【{}】！",_url);
            //扫码成功之后，请求_url
            getMethod = new HttpGet(_url);
            HttpResponse response = getGlobleClient().execute(getMethod);
            return true;
        }

        return false;
    }

    /**替换掉url中的loginSig*/
    public String getCheckLoginUrl(){
        String _url = SmartQQConfigV1.URL_CHECK_LOGIN;
        for(Cookie cookie : this.cookieStore.getCookies()){
            if(cookie.getName().equals("pt_login_sig")){
                _url = _url.replaceAll("(login_sig=)(.*?)&","$1" + cookie.getValue() + "&");
            }
            if(cookie.getName().equals("qrsig")){
                _url = _url.replaceAll("(ptqrtoken=)(.*?)&","$1" + getQrpttoken(cookie.getValue()) + "&");
            }
        }
        return _url;
    }

    public CloseableHttpClient getGlobleClient() {
        return globleClient;
    }

    public void setGlobleClient(CloseableHttpClient globleClient) {
        this.globleClient = globleClient;
    }

    public SSLContext getContext() {
        return context;
    }

    public void setContext(SSLContext context) {
        this.context = context;
    }
}
