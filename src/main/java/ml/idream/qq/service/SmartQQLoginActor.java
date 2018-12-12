package ml.idream.qq.service;

import org.apache.http.client.CookieStore;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import java.util.Iterator;
import java.util.Map;

/**
 * @Description TODO
 * @Author SongJianlong
 * @Date 2018/12/12 15:56
 **/
public class SmartQQLoginActor {

    public static void main(String[] args) {

        CookieStore cookieStore = new BasicCookieStore();
        SSLContext context = SSLContexts.createSystemDefault();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", new PlainConnectionSocketFactory())
                .register("https", new SSLConnectionSocketFactory(context))
                .build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(cookieStore)
                .setRedirectStrategy(new LaxRedirectStrategy())
                .setConnectionManager(connectionManager).setConnectionManagerShared(true).build();

        Iterator<Map.Entry<String, SmartQQAccount>> iterator = SmartQQAccountList.getInstance().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, SmartQQAccount> entry = iterator.next();
            SmartQQLoginService smartQQLoginService = new SmartQQLoginService(client,entry.getValue());
            if(smartQQLoginService.checkLogin()){
                entry.getValue().setLogin(true);
            }else{
                entry.getValue().setLogin(false);
            }
        }
    }
}
