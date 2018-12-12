package ml.idream.qq;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description TODO
 * @Author Aimy
 * @Date 2018/12/5 10:23
 **/
public class SmartQQConfig {

    /**获取pt_login_sig**/
    public static final String URL_LOGIN_SIG = "https://xui.ptlogin2.qq.com/cgi-bin/xlogin?daid=164&target=self&style=40&pt_disable_pwd=1&mibao_css=m_webqq&appid=501004106&enable_qlogin=0&no_verifyimg=1&s_url=https%3A%2F%2Fweb2.qq.com%2Fproxy.html&f_url=loginerroralert&strong_login=1&login_state=10&t=20131024001";

    //二维码
    public static final String URL_EWM = "https://ssl.ptlogin2.qq.com/ptqrshow?appid=501004106&e=2&l=M&s=3&d=72&v=4&t=0.6876138211852272&daid=164&pt_3rd_aid=0";

    //检查是否登陆
    public static final String URL_CHECK_LOGIN = "https://ssl.ptlogin2.qq.com/ptqrlogin?u1=https%3A%2F%2Fweb2.qq.com%2Fproxy.html&ptqrtoken=1201068004&ptredirect=0&h=1&t=1&g=1&from_ui=1&ptlang=2052&action=0-0-1543973642632&js_ver=10289&js_type=1&login_sig=&pt_uistyle=40&aid=501004106&daid=164&mibao_css=m_webqq&";

    //获取用户信息
    public static final String URL_GET_USERINFO = "https://s.web2.qq.com/api/get_self_info2";

    public static final String URL_GET_VFWEBQQ = "https://s.web2.qq.com/api/getvfwebqq?ptwebqq=&clientid=53999199&psessionid=&t=1544521686056";

    /**解析扫码之后的返回值**/
    public static final Pattern patternScan = Pattern.compile("ptuiCB\\('(\\d+)'\\,'(\\d+)','(.*?)?','(\\d+)','(.*?)?', '(.*?)'?\\)");

    /**判断二维码是否有效*/
    public static boolean isLagel(String value){
        Matcher matcher = patternScan.matcher(value);
        if(matcher.find()){
            String flag = matcher.group(1);
            return "65".equals(flag);
        }
        return false;
    }

    /** 获得扫码成功之后的URL*/
    public static String scanSuccessUrl(String value){
        Matcher matcher = patternScan.matcher(value);
        if(matcher.find()){
            String _url = matcher.group(3);
            return _url;
        }
        return "";
    }
}
