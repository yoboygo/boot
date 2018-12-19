package ml.idream.smart;

import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * @Description TODO
 * @Author SongJianlong
 * @Date 2018/12/19 13:25
 **/
public abstract class AbstractSmartLogin {

    private HttpClientContext loginContext;
    private CloseableHttpClient loginClient;

    private String qrCodeGetUrl;
    private String qrCodeCheckUrl;
    private String getUserInfoUrl;
    private String getUserInfoUrlReferer;

    public AbstractSmartLogin(HttpClientContext loginContext, CloseableHttpClient loginClient) {
        this.loginContext = loginContext;
        this.loginClient = loginClient;
    }

    public HttpClientContext getLoginContext() {
        return loginContext;
    }

    public void setLoginContext(HttpClientContext loginContext) {
        this.loginContext = loginContext;
    }

    public String getQrCodeGetUrl() {
        return qrCodeGetUrl;
    }

    public void setQrCodeGetUrl(String qrCodeGetUrl) {
        this.qrCodeGetUrl = qrCodeGetUrl;
    }

    public String getQrCodeCheckUrl() {
        return qrCodeCheckUrl;
    }

    public void setQrCodeCheckUrl(String qrCodeCheckUrl) {
        this.qrCodeCheckUrl = qrCodeCheckUrl;
    }

    public String getGetUserInfoUrl() {
        return getUserInfoUrl;
    }

    public void setGetUserInfoUrl(String getUserInfoUrl) {
        this.getUserInfoUrl = getUserInfoUrl;
    }

    public String getGetUserInfoUrlReferer() {
        return getUserInfoUrlReferer;
    }

    public void setGetUserInfoUrlReferer(String getUserInfoUrlReferer) {
        this.getUserInfoUrlReferer = getUserInfoUrlReferer;
    }
}
