package autologin.base;

import net.sf.json.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 登陆
 * @Author Aimy
 * @Date 2018/9/26 15:05
 **/
public abstract class AbstractLoginProcesser implements ILoginProcesser{

    private static final Logger logger = LoggerFactory.getLogger(AbstractLoginProcesser.class);

    protected ILoginConfig loginConfig;
    protected CloseableHttpClient client;

    public AbstractLoginProcesser(ILoginConfig loginConfig) {
        this.loginConfig = loginConfig;
        this.client = HttpClients.createDefault();
    }

    @Override
    public boolean login(ILoginConfig loginConfig) throws Exception {
//        登陆前
        preLogin();

        logger.info("开始登陆：" + loginConfig.getDesc());

        HttpPost post = new HttpPost();
        post.setURI(new URI(loginConfig.getLoginUrl()));

        logger.info("url：" + loginConfig.getLoginUrl());
        String logInfo = loginConfig.getLoginTemplate().replace("{{userName}}",loginConfig.getUserName()).replace("{{userPassWord}}",loginConfig.getUserPassWord());
        post.setHeaders(loginConfig.getHeaders());
        logger.info("参数为：" + logInfo);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        JSONObject queryInfo = JSONObject.fromObject(logInfo);
        for(Object key : queryInfo.keySet()){
            params.add(new BasicNameValuePair(key.toString(),queryInfo.getString(key.toString())));
        }
        post.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
        CloseableHttpResponse response = this.getHttpClient().execute(post);
        int responseCode = response.getStatusLine().getStatusCode();
        if(responseCode >= 200 && responseCode < 300){
//            登陆成功之后
            afterLoginSuccess();
            return isLogin(EntityUtils.toString(response.getEntity()));
        }
        logger.error(String.format("登陆失败！失败代码 %d ,错误信息 %s !",responseCode,response.getStatusLine().getReasonPhrase()));
        return false;
    }

    private  boolean isLogin(String response) {
        logger.info("返回数据：" + response);
        JSONObject data = JSONObject.fromObject(response);
        return "200".equals(data.getString("code"));
    }

    @Override
    public ILoginConfig getLoginConfig() {
        return loginConfig;
    }

    @Override
    public CloseableHttpClient getHttpClient() {
        return client;
    }
}
