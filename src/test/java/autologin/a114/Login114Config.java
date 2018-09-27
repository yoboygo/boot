package autologin.a114;

import autologin.base.ILoginConfig;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 114登陆配置
 * @Author Aimy
 * @Date 2018/9/26 14:58
 **/
public class Login114Config implements ILoginConfig {

    private static final String HOST = "www.bjguahao.gov.cn";
    private static final String baseUrl = "http://" + HOST;
    private static final String URL_LOGIN = "/quicklogin.htm";
    private static final String URL_ISLOGIN = "/islogin.htm";
    private static final String URL_REFERER = "/index.htm";

    private static final String USER_NAME = "*";
    private static final String USER_PASSWORD = "*";

    @Override
    public String getHost() {
        return HOST;
    }

    @Override
    public String getBaseUrl() {
        return baseUrl;
    }

    @Override
    public String getLoginUrl() {
        return baseUrl + URL_LOGIN;
    }

    @Override
    public String getIsLoginUrl() {
        return baseUrl + URL_ISLOGIN;
    }

    @Override
    public String getUserName() {
        return USER_NAME;
    }

    @Override
    public String getUserPassWord() {
        return USER_PASSWORD;
    }

    @Override
    public String getLoginTemplate() {
        return "{\"isAjax\":true,\"mobileNo\":\"{{userName}}\",\"password\":\"{{userPassWord}}\",\"yzm\":\"\"}";
    }

    @Override
    public Header[] getHeaders() {
        List<Header> headers = new ArrayList<Header>();
        headers.add(new BasicHeader("Referer",baseUrl + URL_REFERER));
        headers.add(new BasicHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; rv:62.0) Gecko/20100101 Firefox/62.0"));
        headers.add(new BasicHeader("X-Requested-With","XMLHttpRequest"));
        headers.add(new BasicHeader("Accept","application/json, text/javascript, */*; q=0.01"));
        headers.add(new BasicHeader("Accept-Encoding","gzip, deflate"));
        headers.add(new BasicHeader("Accept-Language","zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2"));
        headers.add(new BasicHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8"));
        headers.add(new BasicHeader("Host",this.getHost()));
        headers.add(new BasicHeader("Connection","keep-alive"));
        return headers.toArray(new Header[9]);
    }

    @Override
    public String getDesc() {
        return "114";
    }
}
