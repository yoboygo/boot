package ml.idream.qq.entity;


import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;

/**
 * @Description 账号基本信息
 * @Author Aimy
 * @Date 2018/12/12 15:34
 **/
public class SmartQQAccount {

    /** 已经重试的次数*/
    private int tryTimes;

    /** QQ号码*/
    private String account;

    /** 加密后的hash,固定值*/
    private String hash;

    /** vfwebqq*/
    private String vfwebqq;

    /** 请求上下文*/
    private HttpClientContext httpClientContext = HttpClientContext.create();
    {
        httpClientContext.setCookieStore(new BasicCookieStore());
    }

    /** 当前账号是否已经登录*/
    private Boolean isLogin = false;

    public int getTryTimes() {
        return tryTimes;
    }

    public void setTryTimes(int tryTimes) {
        this.tryTimes = tryTimes;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public HttpClientContext getHttpClientContext() {
        return httpClientContext;
    }

    public void setHttpClientContext(HttpClientContext httpClientContext) {
        this.httpClientContext = httpClientContext;
    }

    public Boolean getLogin() {
        return isLogin;
    }

    public void setLogin(Boolean login) {
        isLogin = login;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getVfwebqq() {
        return vfwebqq;
    }

    public void setVfwebqq(String vfwebqq) {
        this.vfwebqq = vfwebqq;
    }
}
