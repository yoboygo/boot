package ml.idream.qq.service;


import org.apache.http.client.protocol.HttpClientContext;

/**
 * @Description 账号基本信息
 * @Author SongJianlong
 * @Date 2018/12/12 15:34
 **/
public class SmartQQAccount {

    /** 已经重试的次数*/
    private int tryTimes;

    /** QQ号码*/
    private String account;

    /** 请求上下文*/
    private HttpClientContext httpClientContext = HttpClientContext.create();

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
}
