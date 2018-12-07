package ml.idream.qq;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @Description SmartQQ业务类
 *
 *  记录所有的cookies
 *  1、获取二维码，将二维码输出到console
 *  2、定时轮询是否扫码
 *  3、如果扫码，跳转到指定连接
 *  4、调用用户接口
 *  参考：http://hc.apache.org/httpclient-3.x/methods.html
 * @Author Aimy
 * @Date 2018/12/5 10:23
 **/
public class SmartQQService {

    private static final Logger logger = LoggerFactory.getLogger(SmartQQService.class);
    //请求过程的cookies
    private CookieStore cookieStore;

    public SmartQQService(CookieStore cookieStore) {
        this.cookieStore = cookieStore;
    }

    /**
     * @Description 获取二维码打印到consoles
     * @Param
     * @return void
     * @Author Aimy
     * @Date
     **/
    public void getEwm() throws IOException {

        HttpGet method = new HttpGet(SmartQQConfig.URL_EWM);
        CloseableHttpClient client = HttpClients.createDefault();
        ImageResponseBody response = client.execute(method,new ImageResponseHander("d:\\ewm",getCookieStore()));
//        client.execute(method);
        for(Header heaer : response.getHeaders()){
            logger.info(heaer.getName() + "-->" + heaer.getValue());
        }
       logger.info("CookiesStore --> {}", JSONArray.fromObject(response.getCookieStore()));
    }


    public static void main(String[] args) throws IOException {
        String path = SmartQQService.class.getClassLoader().getResource("").getPath();
        PropertyConfigurator.configure(path + "/log4j2.yml");
        CookieStore cookieStore = new BasicCookieStore();
        SmartQQService qqLogin = new SmartQQService(cookieStore);
        qqLogin.getEwm();
    }


    public CookieStore getCookieStore() {
        return cookieStore;
    }

    public void setCookieStore(CookieStore cookieStore) {
        this.cookieStore = cookieStore;
    }
}
