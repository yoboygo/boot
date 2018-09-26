package autologin.base;

import org.apache.http.impl.client.CloseableHttpClient;

public interface ILoginConfig {

    String getDesc();

    String getHost();
    String getLoginUrl();
    String getIsLoginUrl();
    String getUserName();
    String getUserPassWord();
    String getLoginTemplate();
    String getReferer();

    CloseableHttpClient getHttpClient();

}
