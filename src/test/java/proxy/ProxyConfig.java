package proxy;

/**
 * @Description 代理服务器配置类
 * @Author Aimy
 * @Date 2018/9/28 9:33
 **/
public class ProxyConfig {
//    提供服务的端口
    private int servicePort = 9091;
//    提供服务的地址
    private String serviceIp= "127.0.0.1";

    public int getServicePort() {
        return servicePort;
    }

    public void setServicePort(int servicePort) {
        this.servicePort = servicePort;
    }

    public String getServiceIp() {
        return serviceIp;
    }

    public void setServiceIp(String serviceIp) {
        this.serviceIp = serviceIp;
    }
}
