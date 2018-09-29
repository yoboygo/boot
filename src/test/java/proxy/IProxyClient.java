package proxy;

public interface IProxyClient {

    default void init() throws Exception {};

    default void destroy() throws Exception {};
}
