package proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Description 代理服务 主流程
 * @Author Aimy
 * @Date 2018/9/28 9:40
 **/
public abstract class AbstractProxyService implements IProxyService {

    private static final Logger logger = LoggerFactory.getLogger(AbstractProxyService.class);

    private ProxyConfig proxyConfig;

    private boolean isShutdown = false;
    private ServerSocket proxySocket;

    public AbstractProxyService(ProxyConfig proxyConfig) {
        this.proxyConfig = proxyConfig;
    }

    @Override
    public void init() throws Exception {
        logger.info("初始化代理服务器---开始");
        proxySocket = new ServerSocket(proxyConfig.getServicePort());

        logger.info("初始化代理服务器---结束");
    }

    @Override
    public void open() throws Exception {
        logger.info(String.format("开启代理服务器：%s:%d",proxyConfig.getServiceIp(),proxyConfig.getServicePort()));
        init();
        //启动当前线程
        while(!isShutdown){
            //浏览器到代理服务器的连接,每一个连接都要启动一个线程
            Socket socket = proxySocket.accept();
            ProxyUtils.getThreadPool().submit(new AcceptHandler(socket));
        }
        destroy();
    }

    @Override
    public void shutdown() throws Exception {
        logger.info("关闭代理服务");
        isShutdown = true;
        proxySocket.close();
    }

    @Override
    public void destroy() throws Exception {
        logger.info("销毁代理服务器");
        if(!isShutdown){
            shutdown();
        }

    }

    @Override
    public boolean isShutdown() {
        return isShutdown;
    }

    @Override
    public ProxyConfig getProxyConfig() {
        return proxyConfig;
    }
}
