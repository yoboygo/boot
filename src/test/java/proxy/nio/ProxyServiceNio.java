package proxy.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import proxy.IProxyService;
import proxy.ProxyConfig;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * @Description NIO 代理
 * @Author Aimy
 * @Date 2018/9/29 17:08
 **/
public class ProxyServiceNio implements IProxyService {

    private ProxyConfig proxyConfig;
    private volatile boolean isShutdown = false;

    private static final Logger logger = LoggerFactory.getLogger(ProxyServiceNio.class);

    public ProxyServiceNio(ProxyConfig proxyConfig) {
        this.proxyConfig = proxyConfig;
    }

    @Override
    public void open() throws Exception {
        try(ServerSocketChannel ssc = ServerSocketChannel.open();
            ServerSocket serverSocket = ssc.socket();
        ){
            //绑定监听端口
            serverSocket.bind(new InetSocketAddress(proxyConfig.getServiceIp(),proxyConfig.getServicePort()));
            ssc.configureBlocking(false);
            Selector selector = Selector.open();
            //注册感兴趣的连接事件
            ssc.register(selector, SelectionKey.OP_ACCEPT);
            logger.info("启动代理服务器...");
            while(!isShutdown){
                int nKeys = selector.select();
                if(nKeys != 0){
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = keys.iterator();
                    while (iterator.hasNext()){
                        SelectionKey key = iterator.next();
                        if(key.isAcceptable()){
                            logger.info("Server 建立连接");
                            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                            try{
                                SocketChannel clientChannel = serverSocketChannel.accept();
                                if(clientChannel == null){
                                    return ;
                                }
                                clientChannel.configureBlocking(false);
                                clientChannel.finishConnect();
                                iterator.remove();
                                //返回浏览器success
                                clientChannel.write(ByteBuffer.wrap("success".getBytes(Charset.forName("UTF-8"))));
                                //注册可读事件，以及处理当前请求的处理器
                                clientChannel.register(selector,SelectionKey.OP_READ | SelectionKey.OP_WRITE,new ProxyProcesser(selector,clientChannel));
                            }catch(Exception e){
                                logger.error("Server建立连接失败！",e);
                            }
                        }else if(key.isReadable()){//可读
                            logger.info("Service 读入数据...");
                            SocketChannel clientChannel = (SocketChannel) key.channel();
                            //获取到当前请求的处理器
                            ProxyProcesser proxyProcesser = (ProxyProcesser) key.attachment();
                            //将数据读取出来
                            proxyProcesser.read(clientChannel);
                            iterator.remove();
                        }else if(key.isWritable()){//可写
                            logger.info("Service 写入数据...");
                            SocketChannel clientChannel = (SocketChannel) key.channel();
                            ProxyProcesser proxyProcesser = (ProxyProcesser) key.attachment();
                            proxyProcesser.write(clientChannel);
                            iterator.remove();
                        }else if(key.isConnectable()){//可供连接
                            SocketChannel sc = (SocketChannel) key.channel();
                            if(sc.isConnectionPending()){//关闭连接
                                sc.finishConnect();
                            }
                            iterator.remove();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void shutdown() throws Exception {
        isShutdown = true;
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
