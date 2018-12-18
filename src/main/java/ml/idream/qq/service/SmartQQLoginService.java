package ml.idream.qq.service;

import ml.idream.qq.common.QRCodeStatus;
import ml.idream.qq.common.SmartQQCommon;
import ml.idream.qq.entity.SmartQQAccount;
import ml.idream.qq.entity.StringResponseBody;
import ml.idream.qq.handler.ImageResponseHander;
import ml.idream.qq.handler.StringResponseHandler;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Calendar;

/**
 * @Description QQ登陆业务类
 * @Author Aimy
 * @Date 2018/12/12 15:17
 **/
public class SmartQQLoginService {

    private static final Logger logger = LoggerFactory.getLogger(SmartQQLoginService.class);

    /**全局客户端*/
    private CloseableHttpClient globleClient;

    /** 账号相关*/
    private SmartQQAccount smartQQAccount;

    public SmartQQLoginService(CloseableHttpClient globleClient,SmartQQAccount smartQQAccount) {
       this.globleClient = globleClient;
       this.smartQQAccount = smartQQAccount;
    }

    /**
     * @Description 检查当前账号是否登陆
     * 第一步：检查能不能获取到用户信息，如果能返回true,如果不能执行第2步
     * 第二步：检查二维码是否被扫描过，如果是，返回true,如果否返回false,如果二维码过期执行第3步
     * 第三步：重新拉取二维码，然后返回false;
     * @Param []
     * @return boolean
     * @Author Aimy
     * @Date
     **/
    public boolean checkLogin() throws IOException {
        JSONObject userInfo = JSONObject.fromObject(getUserInfo());
        if(0 == userInfo.getInt("retcode")){
            smartQQAccount.setAccount(userInfo.getJSONObject("result").getString("account"));
            logger.info("用户【{}】已经登陆！",smartQQAccount.getAccount());
            return true;
        }
        return false;
    }

    /**
     * @Description
     * 检查二维码有没有返回地址，如果有，则请求返回的地址并返回true；如果没有检查二维码是否过期
     * 如果二维码过期，返回false;如果有效但返回true;
     * @Param []
     * @return boolean
     * @Author Aimy
     * @Date  
     **/
    public QRCodeStatus checkQRCode() throws IOException {

        if(!cookiesHas("qrsig")){
            logger.info("没有获取过二维码！");
            return QRCodeStatus.NONE;
        }

        logger.info("检查二维码是否过期...");
        HttpGet getMethod = new HttpGet(getCheckLoginUrl());
        StringResponseBody loginCheckResponse = getGlobleClient().execute(getMethod, new StringResponseHandler(),smartQQAccount.getHttpClientContext());
        logger.info("【{}】", JSONObject.fromObject(loginCheckResponse));

        String _url = SmartQQCommon.scanSuccessUrl(loginCheckResponse.getValue());
        if (StringUtils.isNotBlank(_url)) {//扫码失败返回的_url是""
            logger.info("扫码成功，请求check地址【{}】！",_url);
            //扫码成功之后，请求_url
            getMethod = new HttpGet(_url);
            HttpResponse response = getGlobleClient().execute(getMethod);
            return QRCodeStatus.LOGIN;
        }

        /**二维码失效*/
        if(SmartQQCommon.isLagel(loginCheckResponse.getValue())){
            logger.info("二维码未失效！");
            return QRCodeStatus.EFFECTIVITY;
        }

        return QRCodeStatus.UNEFFECTIVITY;
    }

    /**替换掉url中的loginSig*/
    public String getCheckLoginUrl(){
        String _url = SmartQQCommon.URL_CHECK_LOGIN;
        for(Cookie cookie : this.smartQQAccount.getHttpClientContext().getCookieStore().getCookies()){
            if(cookie.getName().equals("pt_login_sig")){
                _url = _url.replaceAll("(login_sig=)(.*?)&","$1" + cookie.getValue() + "&");
            }
            if(cookie.getName().equals("qrsig")){
                _url = _url.replaceAll("(ptqrtoken=)(.*?)&","$1" + getQrptToken(cookie.getValue()) + "&");
            }
        }
        return _url;
    }
    
    /**
     * @Description 获取二维码和关键cookie qrsign
     * 1、判断cookies中是否含有qrsign,如果有检查二维码是否过期，如果没有，执行第二步；如果二维码过期也执行第二步
     * 2、下载二维码
     * @Param []
     * @return void
     * @Author Aimy
     * @Date
     **/
    public void getQRCode() throws IOException {
        logger.info("开始拉取二维码...");
        HttpGet method = new HttpGet(SmartQQCommon.URL_QRCODE);
        getGlobleClient().execute(method, new ImageResponseHander("d:\\QRCode"),smartQQAccount.getHttpClientContext());
        logger.info("拉取二维码完成!");

    }

    /**
     * @Description 判断cookies中是否含有key
     * @Param []
     * @return boolean
     * @Author Aimy
     * @Date  
     **/
    private boolean cookiesHas(String key) {
        for(Cookie cookie : smartQQAccount.getHttpClientContext().getCookieStore().getCookies()){
            if(cookie.getName().equals(key)){
                return StringUtils.isNotBlank(cookie.getValue());
            }
        }
        return false;
    }

    /**
     * @Description 获取用户的个人信息
     * @Param []
     * @return java.lang.String
     * @Author Aimy
     * @Date
     **/
    public String getUserInfo() throws IOException {
        logger.info("获取个人信息...");
        HttpGet methodGet = new HttpGet(SmartQQCommon.URL_GET_USERINFO + "?t=" + Calendar.getInstance().getTimeInMillis());
        methodGet.setHeader("referer",SmartQQCommon.URL_USERINFO_REFERER);
        StringResponseBody result = getGlobleClient().execute(methodGet,new StringResponseHandler(),getSmartQQAccount().getHttpClientContext());
        logger.info("获取到的个人信息为：【{}】",result.getValue());
        return result.getValue();
    }

    /**
     * @Description 根据qrsign获得qrptToken
     * @Param [qrsign]
     * @return int
     * @Author Aimy
     * @Date
     **/
    public int getQrptToken(String qrsign){
        int e = 0,i,n = qrsign.length();
        for(i = 0;i < n; ++i){
            e += (e << 5) + qrsign.charAt(i);
        }
        return 2147483647 & e;
    }


    public CloseableHttpClient getGlobleClient() {
        return globleClient;
    }

    public void setGlobleClient(CloseableHttpClient globleClient) {
        this.globleClient = globleClient;
    }

    public SmartQQAccount getSmartQQAccount() {
        return smartQQAccount;
    }

    public void setSmartQQAccount(SmartQQAccount smartQQAccount) {
        this.smartQQAccount = smartQQAccount;
    }

}
