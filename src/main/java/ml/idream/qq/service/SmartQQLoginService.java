package ml.idream.qq.service;

import ml.idream.qq.common.QRCodeStatus;
import ml.idream.qq.common.SmartQQCommon;
import ml.idream.qq.entity.SmartQQAccount;
import ml.idream.qq.entity.StringResponseBody;
import ml.idream.qq.handler.Base64ResponseHandler;
import ml.idream.qq.handler.ImageResponseHander;
import ml.idream.qq.handler.StringResponseHandler;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
    public boolean checkLogin() throws Exception {
        try{
            JSONObject userInfo = JSONObject.fromObject(getUserInfo());
            int retCode = userInfo.getInt("retcode");
            if(0 == retCode || 100003 == retCode){
            /*JSONObject retObject = userInfo.getJSONObject("result");
            if(retObject.containsKey("account")){
                smartQQAccount.setAccount(retObject.getString("account"));
            }else if(retObject.containsKey("vfwebqq")){
                smartQQAccount.setAccount(retObject.getString("vfwebqq"));
            }
            logger.info("用户【{}】已经登陆！",smartQQAccount.getAccount());*/
                return true;
            }
        }catch (Exception e){
            logger.info("检查用户登录失败!",e);
            throw new Exception("检查用户登录失败!" + e.getMessage());
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
    public QRCodeStatus checkQRCode() throws Exception {

        try{

            if(!cookiesHas("qrsig")){
                logger.info("没有获取过二维码！");
                return QRCodeStatus.NONE;
            }

            logger.info("检查二维码是否过期...");
            String _urlCheck = getCheckLoginUrl();
            logger.info("检查二维码地址【{}】",_urlCheck);
            HttpGet getMethod = new HttpGet(_urlCheck);
            Header headerCookie = convertCookieToHeader(smartQQAccount.getHttpClientContext().getCookieStore().getCookies());
            getMethod.addHeader(headerCookie);
            logger.info("请求Cookie【{}】", JSONArray.fromObject(smartQQAccount.getHttpClientContext().getCookieStore().getCookies()));
            StringResponseBody loginCheckResponse = getGlobleClient().execute(getMethod, new StringResponseHandler(),smartQQAccount.getHttpClientContext());
            logger.info("返回结果为：【{}】", JSONObject.fromObject(loginCheckResponse));

            String _url = SmartQQCommon.scanSuccessUrl(loginCheckResponse.getValue());
            if (StringUtils.isNotBlank(_url)) {//扫码失败返回的_url是""
                logger.info("扫码成功，请求check地址【{}】！",_url);
                //扫码成功之后，请求_url
                getMethod = new HttpGet(_url);
                HttpResponse response = getGlobleClient().execute(getMethod,smartQQAccount.getHttpClientContext());
                int status = response.getStatusLine().getStatusCode();
                if(status >= 200){
                    logger.info("扫完码之后跳转url，为获取cookie。。。");
                }
                return QRCodeStatus.LOGIN;
            }

            /**二维码失效*/
            if(SmartQQCommon.isLagel(loginCheckResponse.getValue())){
                logger.info("二维码未失效！");
                return QRCodeStatus.EFFECTIVITY;
            }

            return QRCodeStatus.UNEFFECTIVITY;
        }catch (Exception e){
            logger.error("检查二维码失败！",e);
            throw new Exception("检查二维码失败！" + e.getMessage());
        }
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
    public void getQRCode() throws Exception {
        logger.info("开始拉取二维码...");
        try {
            HttpGet method = new HttpGet(SmartQQCommon.URL_QRCODE);
            getGlobleClient().execute(method, new ImageResponseHander("d:\\QRCode"),smartQQAccount.getHttpClientContext());
            logger.info("拉取二维码完成!");
        }catch (Exception e){
            logger.error("拉取二维码失败！",e);
            throw new Exception("拉取二维码失败！" + e.getMessage());
        }


    }


    /**
     * @Description 获取Base64加密的二维码
     * @Param []
     * @return 二维码
     * @Author Aimy
     * @Date 2018/12/27 15:03
     **/
    public String getQRCodeBase64() throws Exception {
        logger.info("开始拉取二维码...");
        try {
            HttpGet method = new HttpGet(SmartQQCommon.URL_QRCODE);
            String qrCode = getGlobleClient().execute(method, new Base64ResponseHandler(),smartQQAccount.getHttpClientContext());
            logger.info("拉取二维码完成!");
            return qrCode;
        }catch (Exception e){
            logger.error("拉取二维码失败！",e);
            throw new Exception("拉取二维码失败！" + e.getMessage());
        }
    }

   /**
    * @Description 登陆成功后，获取后期的关键参数
    * @Param []
    * @return void
    * @Author Aimy
    * @Date 2018/12/27 15:03
    **/
    public void affterLogin() throws Exception {
        JSONObject login2 = login2();
        String uin = login2.getJSONObject("result").getString("uin");
        smartQQAccount.setAccount(uin);
        String vfwebqq = getvfwebqq();
        smartQQAccount.setVfwebqq(vfwebqq);
        String hash = getHash(uin,"");
        smartQQAccount.setHash(hash);
    }

    /**
     * @Description 获取vfwebqq
     * @Param []
     * @return java.lang.String
     * @Author Aimy
     * @Date  
     **/
    public String getvfwebqq() throws Exception {
        try{
            logger.info("开始获取vfwebbqq");
            HttpGet method = new HttpGet(SmartQQCommon.URL_GET_VFWEBQQ);
            method.addHeader("referer",SmartQQCommon.URL_GET_VFWEBQQ_REFERER);
            method.addHeader(convertCookieToHeader(smartQQAccount.getHttpClientContext().getCookieStore().getCookies()));
            StringResponseBody result = getGlobleClient().execute(method, new StringResponseHandler(),smartQQAccount.getHttpClientContext());
            logger.info("获取到的vfwebqq:【{}】",result.getValue());
            String vfwebqq = JSONObject.fromObject(result.getValue()).getJSONObject("result").getString("vfwebqq");
            return vfwebqq;
        }catch (Exception e){
            logger.error("获取vfwebqq错误！",e);
            throw new Exception("获取vfwebqq错误！" + e.getMessage());
        }
    }

    /**
     * @Description 获取account
     * @Param []
     * @return 账号
     * @Author Aimy
     * 以下为借口返回的数据
     * {
     * 	"result": {
     * 		"cip": 23600812,
     * 		"f": 0,
     * 		"index": 1075,
     * 		"port": 47450,
     * 		"psessionid": "8368046764001d636f6e6e7365727665725f77656271714031302e3133332e34312e383400001ad00000066b026e040015808a206d0000000a406172314338344a69526d0000002859185d94e66218548d1ecb1a12513c86126b3afb97a3c2955b1070324790733ddb059ab166de6857",
     * 		"status": "online",
     * 		"uin": 917708483,
     * 		"user_state": 0,
     * 		"vfwebqq": "59185d94e66218548d1ecb1a12513c86126b3afb97a3c2955b1070324790733ddb059ab166de6857"
     * 	},
     * 	"retcode": 0
     * }
     * @Date  
     **/
    public JSONObject login2() throws Exception {
        try{
            logger.info("login2。。。");
            HttpPost methPost = new HttpPost(SmartQQCommon.URL_POST_LOGIN2);
            methPost.addHeader("referer",SmartQQCommon.URL_POST_LOGIN2_REFERER);
            methPost.addHeader(convertCookieToHeader(smartQQAccount.getHttpClientContext().getCookieStore().getCookies()));
            String pValue = "{\"ptwebqq\":\"\",\"clientid\":53999199,\"psessionid\":\"\",\"status\":\"online\"}";
            methPost.setEntity(getSingleParamEntity("r",pValue));
            StringResponseBody result = getGlobleClient().execute(methPost,new StringResponseHandler(),smartQQAccount.getHttpClientContext());
            JSONObject retValue = JSONObject.fromObject(result.getValue());
            logger.info("获取用户信息：【{}】",result.getValue());
            return retValue;
        }catch (Exception e){
            logger.error("Login2失败！",e);
            throw new Exception("Login2失败！" + e.getMessage());
        }
    }

    /**
     * @Description 获取好友列表
     * @Param []
     * @return JSON格式的好友列表
     * @Author Aimy
     * @Date  
     **/
    public String getFriends() throws Exception {
        try{
            logger.info("获取好友列表。。。");
            HttpPost methPost = new HttpPost(SmartQQCommon.URL_USER_FRIENDS);
            methPost.addHeader("referer",SmartQQCommon.URL_USER_FRIENDS_REFERER);
            methPost.addHeader(convertCookieToHeader(smartQQAccount.getHttpClientContext().getCookieStore().getCookies()));

            JSONObject pValue = new JSONObject();
            pValue.put("vfwebqq",smartQQAccount.getVfwebqq());
            pValue.put("hash",smartQQAccount.getHash());
            methPost.setEntity(getSingleParamEntity("r",pValue.toString()));

            StringResponseBody result = getGlobleClient().execute(methPost,new StringResponseHandler(),smartQQAccount.getHttpClientContext());
            logger.info("获取【{}】好友列表：【{}】",smartQQAccount.getAccount(),result.getValue());
            return result.getValue();
        }catch (Exception e){
            logger.error("获取好友信息错误！",e);
            throw new Exception("获取好友信息错误！" + e.getMessage());
        }
    }

    /**
     * @Description 拼装POST请求的Entity
     * {"vfwebqq":"33497c2f75077330f0f78dfefbb2de9115f9b6ebdd3e021beb65d0445c2281d295331fafe8169c7e","hash":"007300F000510088"}
     * @Param [key, value]
     * @return
     * @Author Aimy
     * @Date  
     **/
    private HttpEntity getSingleParamEntity(String key, String value){
        NameValuePair valuePair = new BasicNameValuePair(key,value);
        List<NameValuePair> params = new ArrayList<>();
        params.add(valuePair);
        return new UrlEncodedFormEntity(params,Charset.forName("utf-8"));
    }

    /**
     * @Description 判断cookies中是否含有key
     * @Param []
     * @return boolean
     * @Author Aimy
     * @Date  
     **/
    private boolean cookiesHas(String key) {
        if(smartQQAccount.getHttpClientContext().getCookieStore() == null){
            return false;
        }
        for(Cookie cookie : smartQQAccount.getHttpClientContext().getCookieStore().getCookies()){
            if(cookie.getName().equals(key)){
                return StringUtils.isNotBlank(cookie.getValue());
            }
        }
        return false;
    }

    /**
     * @Description 获取用户的个人信息
     * 但是返回的格式数据时有不同
     * @Param []
     * @return JSON格式的用户信息
     * @Author Aimy
     * @Date
     **/
    public String getUserInfo() throws Exception {
        logger.info("获取个人信息...");
        try {
            HttpGet methodGet = new HttpGet(SmartQQCommon.URL_GET_USERINFO + "?t=" + Calendar.getInstance().getTimeInMillis());
            methodGet.setHeader("referer",SmartQQCommon.URL_USERINFO_REFERER);
            methodGet.addHeader(convertCookieToHeader(smartQQAccount.getHttpClientContext().getCookieStore().getCookies()));
            logger.info("Cookie:【{}】",smartQQAccount.getHttpClientContext().getCookieStore().getCookies());
            StringResponseBody result = getGlobleClient().execute(methodGet,new StringResponseHandler(),smartQQAccount.getHttpClientContext());

            logger.info("获取到的个人信息为：【{}】",result.getValue());
            return result.getValue();
        }catch (Exception e){
            logger.error("获取个人信息失败！",e);
            throw new Exception("获取个人信息失败！" + e.getMessage());
        }
    }

    private Header convertCookieToHeader(List<Cookie> cookies ){
        if(cookies == null){
            return new BasicHeader("Cookie","");
        }
        StringBuilder sb = new StringBuilder();
        for(Cookie cookie : cookies){
            sb.append(cookie.getName()).append("=").append(cookie.getValue()).append(";");
        }
        return new BasicHeader("Cookie",StringUtils.substringBeforeLast(sb.toString(),";"));
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

    /**
     * @Description 获取好友参数中hash的加密算法 ptfwebqq全部为""
     * @Param [uin, ptfwebqq]
     * @return java.lang.Integer
     * @Author Aimy
     * @Date  
     **/
    public String getHash(String uin, String ptfwebqq) throws FileNotFoundException, ScriptException, NoSuchMethodException {
        String _path = SmartQQLoginService.class.getResource("/").getPath() + "script/smart.js";
        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine jsEngine = sem.getEngineByName("js");
        jsEngine.eval(new InputStreamReader(new FileInputStream(new File(_path))));
        Invocable jsInvoke = (Invocable) jsEngine;
        Object result = jsInvoke.invokeFunction("hash2",new Object[]{uin,ptfwebqq});
        return result.toString();
    }

    public void closeClient() throws IOException {
        getGlobleClient().close();
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
