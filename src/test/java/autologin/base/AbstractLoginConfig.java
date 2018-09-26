package autologin.base;

import net.sf.json.JSONObject;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * @Description
 * @Author Aimy
 * @Date 2018/9/26 14:47
 **/
public abstract class AbstractLoginConfig implements ILoginConfig {

    CloseableHttpClient httpClient;

    public AbstractLoginConfig() {
        this.httpClient = HttpClients.createDefault();
    }

    @Override
    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }

}
