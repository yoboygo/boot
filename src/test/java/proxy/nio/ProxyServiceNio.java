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
    private boolean isShutdown = false;

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
                                SocketChannel channel = serverSocketChannel.accept();
                                if(channel == null){
                                    return ;
                                }
                                channel.configureBlocking(false);
                                channel.finishConnect();
                                iterator.remove();
                                //返回浏览器success
                                channel.write(ByteBuffer.wrap("success".getBytes(Charset.forName("UTF-8"))));
                                //注册可读时间
                                channel.register(selector,SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                            }catch(Exception e){
                                logger.error("Server建立连接失败！",e);
                            }
                        }else if(key.isReadable()){//可读
                            logger.info("Service 读入数据...");
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            SocketChannel sc = (SocketChannel) key.channel();
                            int len = 0;
                            StringBuilder readInfo =  new StringBuilder();
                            //读取目前可读的流，sc.read返回的为成功复制到bytebuffer中的字节数，此步骤为阻塞操作，值为0；
                            //当已经是流的结尾时，返回-1
                            try{
                                while((len = sc.read(buffer)) >= 0){
                                    String datas = new String(buffer.array(),Charset.forName("UTF-8"));
                                    readInfo.append(datas);
                                }
                                //读取请求第一行，并将数据发送给远程服务器
                                key.attachment();
                                //读取数据的剩余部分，将数据发送给远程服务器
                            }catch (Exception e){
                                logger.error("从浏览器读数据失败",e);
                            }finally {
                                buffer.flip();
                            }
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
