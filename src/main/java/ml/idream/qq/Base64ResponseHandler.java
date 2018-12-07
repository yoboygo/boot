package ml.idream.qq;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Description
 * @Author Aimy
 * @Date 2018/12/7 14:22
 **/
public class Base64ResponseHandler implements ResponseHandler<String> {
    @Override
    public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        int status = response.getStatusLine().getStatusCode();
        if(status >= 200 && status < 300){
            InputStream is = response.getEntity().getContent();
            BASE64Encoder encoder = new BASE64Encoder();
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            return encoder.encode(bytes);
        }else{
            throw new ClientProtocolException("Unexpected response status: " + status);
        }
    }
}
