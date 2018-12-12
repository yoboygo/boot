package ml.idream.qq.handler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @Description 将返回的结果处理成String
 * @Author Aimy
 * @Date 2018/12/7 11:40
 **/
public class StringResponseHandler implements ResponseHandler<String> {
    private static final Logger logger = LoggerFactory.getLogger(StringResponseHandler.class);

    @Override
    public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        int status =  response.getStatusLine().getStatusCode();
        if(status >= 200 && status < 300){
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, Charset.forName("UTF-8"));
            logger.info("将返回值转换为字符串：【{}】",responseString);
            return entity == null ? null : responseString;
        }else{
            throw new ClientProtocolException("Unexpected response status: " + status);
        }
    }
}
