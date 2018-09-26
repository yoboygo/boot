package autologin.base;

import net.sf.json.JSONObject;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @Description 登陆
 * @Author Aimy
 * @Date 2018/9/26 15:05
 **/
public abstract class AbstractLoginProcesser implements ILoginProcesser{

    private static final int tryTimes = 3;
    private static final int counter = 0;
    private static final Logger logger = LoggerFactory.getLogger(AbstractLoginProcesser.class);

    @Override
    public boolean login(ILoginConfig loginConfig) throws URISyntaxException, IOException {

        logger.info("开始登陆：" + loginConfig.getDesc());

        HttpPost post = new HttpPost();
        post.setURI(new URI(loginConfig.getLoginUrl()));

        logger.info("url：" + loginConfig.getLoginUrl());

        String logInfo = loginConfig.getLoginTemplate().replace("{{userName}}",loginConfig.getUserName()).replace("{{userPassWord}}",loginConfig.getUserPassWord());
        post.setEntity(new StringEntity(logInfo,"UTF-8"));
        post.setHeader("Referer",loginConfig.getReferer());
        post.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; rv:62.0) Gecko/20100101 Firefox/62.0");
        logger.info("参数为：" + logInfo);

        CloseableHttpResponse response = loginConfig.getHttpClient().execute(post);
        int responseCode = response.getStatusLine().getStatusCode();
        if(responseCode == 302){//重定向
            logger.info("发现重定向地址:");
            Header location = response.getFirstHeader("Location");
            logger.info(location.getValue());
            post.setHeader("Referer",loginConfig.getLoginUrl());
            post.setURI(new URI(loginConfig.getHost() + location.getValue()));
            response = loginConfig.getHttpClient().execute(post);
        }
        return isLogin(EntityUtils.toString(response.getEntity()));
    }

    private  boolean isLogin(String response) {
        logger.info("返回数据：" + response);
        JSONObject data = JSONObject.fromObject(response);
        return "200".equals(data.getString("code"));
    }
}
