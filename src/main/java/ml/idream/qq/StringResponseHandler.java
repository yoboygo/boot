package ml.idream.qq;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @Description 将返回的结果处理成String
 * @Author Aimy
 * @Date 2018/12/7 11:40
 **/
public class StringResponseHandler implements ResponseHandler<String> {

    @Override
    public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        int status =  response.getStatusLine().getStatusCode();
        if(status >= 200 && status < 300){
            HttpEntity entity = response.getEntity();
            return entity == null ? null : EntityUtils.toString(entity);
        }else{
            throw new ClientProtocolException("Unexpected response status: " + status);
        }
    }
}
