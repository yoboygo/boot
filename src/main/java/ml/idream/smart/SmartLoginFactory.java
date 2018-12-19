package ml.idream.smart;

import org.apache.http.impl.client.CloseableHttpClient;

public interface SmartLoginFactory {

    CloseableHttpClient getLoginClient();

    default void doLogin(){

    }



}
