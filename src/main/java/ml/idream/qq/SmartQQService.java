package ml.idream.qq;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.ConnectionConfig;
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
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.SimpleScriptContext;
import java.io.IOException;
import java.util.regex.Pattern;

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
public class SmartQQService extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(SmartQQService.class);
    //请求的上下文环境
    private SSLContext context;
    //全局客户端
    private CloseableHttpClient globleClient;

    /**cookieStore*/
    private CookieStore cookieStore;

    public SmartQQService() {
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

    public void getLoginSig() throws IOException {
        logger.info("【准备获取LoginSig】");
        HttpGet method = new HttpGet(SmartQQConfig.URL_LOGIN_SIG);
        getGlobleClient().execute(method,new StringResponseHandler());
        logger.info("【获取LoginSig成功】");
    }

    /**
     * @Description 将qrsig转换成token
     * @Param [qrsig]
     * @return int
     * @Author SongJianlong
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
        logger.info("【开始拉取二维码】");
        HttpGet method = new HttpGet(SmartQQConfig.URL_EWM);
        getGlobleClient().execute(method, new ImageResponseHander("d:\\ewm"));
//        client.execute(method);
        logger.info("【拉取二维码完成】");

    }


    public static void main(String[] args) throws IOException {
        BasicConfigurator.configure();
        String path = SmartQQService.class.getClassLoader().getResource("").getPath();
        PropertyConfigurator.configure(path + "/log4j2.yml");

        SmartQQService qqLogin = new SmartQQService();
        qqLogin.start();

//        System.out.println(qqLogin.getQrpttoken("ctS6SCAobnpJkamJLtvQOYFP3x5PH0mP5eiLosLIvO-gO4iUAhoahm6f4eJiusvA"));
    }


    /**
     * @return void
     * @Description 1、获取二维码，存放到指定目录
     * 2、每隔1s轮询二维码是否过期，如果过期重新拉取，如果有人扫码，跳转到指定页面，然后请求个人信息
     * @Param []
     * @Author SongJianlong
     * @Date
     **/
    @Override
    public void run() {
        try {

            getLoginSig();
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
     * @Author SongJianlong
     * @Date
     **/
    private void getUserInfo() {
        logger.info("------登陆成功，获取个人信息-------");
    }

    /**
     * @return
     * @Description 检查二维码是否被扫过
     * @Param
     * @Author SongJianlong
     * @Date
     **/
    private boolean checkScand() throws IOException {
        logger.info("------检查二维码是否过期-------");
        HttpGet getMethod = new HttpGet(getCheckLoginUrl());
        RequestConfig requestConfig = RequestConfig.custom().setRedirectsEnabled(true).setCircularRedirectsAllowed(true).build();
        getMethod.setConfig(requestConfig);
//        getMethod.setHeader(":authority","ssl.ptlogin2.qq.com");
//        getMethod.setHeader(":method","GET");
//        getMethod.setHeader(":path",getCheckLoginUrl());
//        getMethod.setHeader(":scheme","https");
//        getMethod.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36");
//        getMethod.setHeader("referer", "https://xui.ptlogin2.qq.com/cgi-bin/xlogin?daid=164&target=self&style=40&pt_disable_pwd=1&mibao_css=m_webqq&appid=501004106&enable_qlogin=0&no_verifyimg=1&s_url=https%3A%2F%2Fweb2.qq.com%2Fproxy.html&f_url=loginerroralert&strong_login=1&login_state=10&t=20131024001");
        String loginSigUrl = getGlobleClient().execute(getMethod, new StringResponseHandler());
        logger.info("-------{}", loginSigUrl);
        String _url = SmartQQConfig.scanSuccessUrl(loginSigUrl);
        if (StringUtils.isNotBlank(_url)) {//扫码失败返回的_url是""
            logger.info("------扫码成功！-------");
            //扫码成功之后，请求_url
            getMethod = new HttpGet(_url);
            HttpResponse response = getGlobleClient().execute(getMethod);
            return true;
        }

        return false;
    }

    /**替换掉url中的loginSig*/
    public String getCheckLoginUrl(){
        String _url = SmartQQConfig.URL_CHECK_LOGIN;
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
