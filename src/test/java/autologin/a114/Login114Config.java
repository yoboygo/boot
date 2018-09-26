package autologin.a114;

import autologin.base.AbstractLoginConfig;

/**
 * @Description
 * @Author Aimy
 * @Date 2018/9/26 14:58
 **/
public class Login114Config extends AbstractLoginConfig {

    private static final String HOST = "http://www.bjguahao.gov.cn";
    private static final String URL_LOGIN = "/quicklogin.htm";
    private static final String URL_ISLOGIN = "/islogin.htm";
    private static final String URL_REFERER = "/index.htm";

    private static final String USER_NAME = "17695676886";
    private static final String USER_PASSWORD = "zpl123";

    @Override
    public String getHost() {
        return HOST;
    }

    @Override
    public String getLoginUrl() {
        return HOST + URL_LOGIN;
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
    public String getIsLoginUrl() {
        return HOST + URL_ISLOGIN;
    }

    @Override
    public String getReferer() {
        return HOST + URL_REFERER;
    }

    @Override
    public String getLoginTemplate() {
        return "{\"isAjax\":true,\"mobileNo\":\"{{userName}}\",\"password\":\"{{userPassWord}}\",\"yzm\":\"\"}";
    }

    @Override
    public String getDesc() {
        return "登陆114";
    }
}
