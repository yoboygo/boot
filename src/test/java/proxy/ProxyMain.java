package proxy;

import proxy.impl.OrignProxyService;
import proxy.nio.ProxyServiceNio;

/**
 * @Description 代理服务主类
 * @Author Aimy
 * @Date 2018/9/28 14:10
 **/
public class ProxyMain {

    public static void main(String[] args) throws Exception {
        ProxyConfig proxyConfig = new ProxyConfig();
//        OrignProxyService proxyService = new OrignProxyService(proxyConfig);
//        proxyService.open();
        ProxyServiceNio nioProxy = new ProxyServiceNio(proxyConfig);
        nioProxy.open();
        Thread.currentThread().join();
    }
}
