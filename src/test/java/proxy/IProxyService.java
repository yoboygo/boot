package proxy;

public interface IProxyService{

    default void init()throws Exception {} ;
    void open()throws Exception;
    void shutdown() throws Exception;
    boolean isShutdown();
    default void destroy()throws Exception {};

    ProxyConfig getProxyConfig();

}
