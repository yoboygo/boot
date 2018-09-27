package autologin.base;

import org.apache.http.Header;

public interface ILoginConfig {

    String getDesc();

    String getHost();
    String getBaseUrl();
    String getLoginUrl();
    String getIsLoginUrl();
    String getUserName();
    String getUserPassWord();
    String getLoginTemplate();
    Header[] getHeaders();

}
