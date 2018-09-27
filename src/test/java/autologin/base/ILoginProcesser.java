package autologin.base;

import org.apache.http.impl.client.CloseableHttpClient;

import java.util.List;

//处理的登陆的接口
public interface ILoginProcesser {

    default void preLogin() throws Exception {};
    //登陆方法
    boolean login(ILoginConfig loginConfig) throws Exception;

    default void afterLoginSuccess() throws Exception {};

    List<Hospital> getHospitalList(boolean init);

    ILoginConfig getLoginConfig();

    CloseableHttpClient getHttpClient();

    default void destroy() {};
}
