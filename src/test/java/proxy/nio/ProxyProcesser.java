package proxy.nio;

import org.apache.http.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import proxy.ProxyUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;

/**
 * @Description 代理处理器
 * @Author Aimy
 * @Date 2018/10/23 14:21
 **/
public class ProxyProcesser {

    private static final Logger logger = LoggerFactory.getLogger(ProxyProcesser.class);

    private Selector selector;

    private SocketChannel clientChannel;//浏览器端的channel
    private SocketChannel serverChannel;//远程服务器端的channel

    private LinkedList<ByteBuffer> clientData = new LinkedList<ByteBuffer>();
    private LinkedList<ByteBuffer> serverData = new LinkedList<ByteBuffer>();


    public ProxyProcesser(Selector selector,SocketChannel clientChannel) throws IOException {
        this.selector = selector;
        this.clientChannel = clientChannel;
        this.serverChannel = SocketChannel.open();//初始化远程服务器的channel
        this.serverChannel.configureBlocking(false);
    }

    /**
     * @Description 从浏览器读取数据，转发到服务器,或者将从远程服务器返回的数据转发到浏览器
     * @Param [sc]
     * @return int
     * @Author Aimy
     * @Date  
     **/
    public void read(SocketChannel sc) throws Exception {
        //读取目前可读的流，sc.read返回的为成功复制到bytebuffer中的字节数，此步骤为阻塞操作，值为0；
        //当已经是流的结尾时，返回-1
        if(clientChannel.equals(sc)){//读取浏览器数据
            logger.info("读取浏览器数据...");
            String requestInfo = ProxyUtils.readRequest(sc);
            Header[] headers = ProxyUtils.readHeaders(requestInfo);
            InetSocketAddress inetSocketAddress = ProxyUtils.getInetSocketAddress(headers);
            serverChannel.register(selector, SelectionKey.OP_CONNECT,this);
            serverChannel.connect(inetSocketAddress);//连接远程服务器
            //暂存读取的数据
            clientData.add(ByteBuffer.wrap(requestInfo.getBytes("UTF-8")));
        }else if(serverChannel.equals(sc)){//读取服务端数据
            logger.info("读取服务端数据...");
            String requestInfo = ProxyUtils.readRequest(sc);
            serverData.add(ByteBuffer.wrap(requestInfo.getBytes("UTF-8")));
        }

    }

    /**
     * @Description 将远程服务器返回的数据，写回浏览器或者将浏览器发送的数据写到远程服务器
     * @Param [sc]
     * @return void
     * @Author Aimy
     * @Date  
     **/
    public void write(SocketChannel sc) throws IOException {

        if(clientChannel.equals(sc) && !serverData.isEmpty()){//将服务器返回的数据写回到浏览器
            while(!serverData.isEmpty()){
                logger.info("向浏览器写数据...");
                ByteBuffer buffer = serverData.getFirst();
                while (buffer.remaining() > 0){
                    clientChannel.write(buffer);
                }
                serverData.removeFirst();
            }
        }else if(serverChannel.equals(sc)){//将从浏览器接收到的数据写到远程服务端
            logger.info("向服务端写数据...");
            ByteBuffer buffer = clientData.getFirst();
            while(!clientData.isEmpty()){
                serverChannel.write(buffer);
            }
            clientData.removeFirst();
        }
    }
}
