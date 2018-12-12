package ml.idream.qq.service;

import ml.idream.qq.common.SmartQQCommon;
import ml.idream.qq.handler.ImageResponseHander;
import ml.idream.qq.handler.StringResponseHandler;
import ml.idream.qq.v1.SmartQQConfigV1;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @Description QQ登陆业务类
 * @Author SongJianlong
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
     * @Author SongJianlong
     * @Date
     **/
    public boolean checkLogin(){
        JSONObject userInfo = JSONObject.fromObject(getUserInfo());
        if(0 == userInfo.getInt("retcode")){
            return true;
        }
        if(checkQRCode()){
            return true;
        }
        return false;
    }

    /**
     * @Description 检查二维码状态，如果二维码过期，
     * 重新拉取并返回false;如果有效但是没有扫码返回false;扫过返回true
     * @Param []
     * @return boolean
     * @Author SongJianlong
     * @Date  
     **/
    private boolean checkQRCode() throws IOException {
        logger.info("检查二维码是否过期...");
        HttpGet getMethod = new HttpGet(getCheckLoginUrl());
        String loginCheckResponse = getGlobleClient().execute(getMethod, new StringResponseHandler(),smartQQAccount.getHttpClientContext());
        logger.info("【{}】", loginCheckResponse);
        if(SmartQQConfigV1.isLagel(loginCheckResponse)){/**二维码失效，重新获取二维码 */
            getQRCode();
            return false;
        }
        String _url = SmartQQConfigV1.scanSuccessUrl(loginCheckResponse);
        if (StringUtils.isNotBlank(_url)) {//扫码失败返回的_url是""
            logger.info("扫码成功，请求check地址【{}】！",_url);
            //扫码成功之后，请求_url
            getMethod = new HttpGet(_url);
            HttpResponse response = getGlobleClient().execute(getMethod);
            return true;
        }

        return false;
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
     * @Param []
     * @return void
     * @Author SongJianlong
     * @Date
     **/
    public void getQRCode() throws IOException {
        logger.info("开始拉取二维码...");
        HttpGet method = new HttpGet(SmartQQCommon.URL_QRCODE);
        getGlobleClient().execute(method, new ImageResponseHander("d:\\ewm",smartQQAccount.getAccount()),smartQQAccount.getHttpClientContext());
        logger.info("拉取二维码完成!");

    }


    public String getUserInfo(){

        return null;
    }

    /**
     * @Description 根据qrsign获得qrptToken
     * @Param [qrsign]
     * @return int
     * @Author SongJianlong
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
