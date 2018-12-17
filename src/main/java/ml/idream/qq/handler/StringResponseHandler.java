package ml.idream.qq.handler;

import ml.idream.qq.entity.StringResponseBody;
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
public class StringResponseHandler implements ResponseHandler<StringResponseBody> {
    private static final Logger logger = LoggerFactory.getLogger(StringResponseHandler.class);

    @Override
    public StringResponseBody handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        StringResponseBody result = new StringResponseBody();
        result.setStatusLine(response.getStatusLine());
        try{
            String responseString = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
            result.setValue(responseString);
        }catch (Exception e){
            throw new ClientProtocolException("Unexoected response values:" + e.getMessage());
        }

        return result;
    }
}
