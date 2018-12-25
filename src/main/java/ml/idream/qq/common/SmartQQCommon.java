package ml.idream.qq.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description 通用配置
 * @Author Aimy
 * @Date 2018/12/12 16:10
 **/
public class SmartQQCommon {

    /** 登录最大重试次数，超过此值不再重试*/
    public static final int maxTry = 3;

    public static final String URL_QRCODE = "https://ssl.ptlogin2.qq.com/ptqrshow?appid=501004106&e=2&l=M&s=3&d=72&v=4&t=0.6876138211852272&daid=164&pt_3rd_aid=0";

    public static final String URL_CHECK_LOGIN = "https://ssl.ptlogin2.qq.com/ptqrlogin?u1=https%3A%2F%2Fweb2.qq.com%2Fproxy.html&ptqrtoken=1201068004&ptredirect=0&h=1&t=1&g=1&from_ui=1&ptlang=2052&action=0-0-1543973642632&js_ver=10289&js_type=1&login_sig=&pt_uistyle=40&aid=501004106&daid=164&mibao_css=m_webqq&";

    //获取用户信息
    public static final String URL_GET_USERINFO = "https://s.web2.qq.com/api/get_self_info2";

    public static final String URL_USERINFO_REFERER = "https://d1.web2.qq.com/proxy.html?v=20151105001&callback=1&id=2";


    public static final String URL_GET_VFWEBQQ = "https://s.web2.qq.com/api/getvfwebqq?ptwebqq=&clientid=53999199&psessionid=&t=1545721819334";
    public static final String URL_GET_VFWEBQQ_REFERER = "https://s.web2.qq.com/proxy.html?v=20130916001&callback=1&id=1";

    public static final String URL_POST_LOGIN2 = "https://d1.web2.qq.com/channel/login2";
    public static final String URL_POST_LOGIN2_REFERER = "https://d1.web2.qq.com/proxy.html?v=20151105001&callback=1&id=2";

    public static final String URL_USER_FRIENDS = "https://s.web2.qq.com/api/get_user_friends2";
    public static final String URL_USER_FRIENDS_REFERER = "https://s.web2.qq.com/proxy.html?v=20130916001&callback=1&id=1";

    /**解析扫码之后的返回值**/
    public static final Pattern patternScan = Pattern.compile("ptuiCB\\('(\\d+)'\\,'(\\d+)','(.*?)?','(\\d+)','(.*?)?', '(.*?)'?\\)");

    /**判断二维码是否有效*/
    public static boolean isLagel(String value){
        Matcher matcher = patternScan.matcher(value);
        if(matcher.find()){
            int flag = Integer.parseInt(matcher.group(1));
            return flag == QRCodeStatus.EFFECTIVITY.getValue() || flag == QRCodeStatus.CHECKING.getValue();
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

    public static void main(String[] args) {
        String value = "ptuiCB('67','0','','0',''二维码认证中(3996152704)', '')";
//        String value = "ptuiCB('66','0','','0','二维码未失效。(3996152704)', '')";
        System.out.println(SmartQQCommon.isLagel(value));
    }
}
