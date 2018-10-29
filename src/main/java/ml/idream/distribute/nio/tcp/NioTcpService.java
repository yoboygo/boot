package ml.idream.distribute.nio.tcp;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;

/**
 * TCP NIO Service
 * @page 7
 * @author Aimy
 *
 */
public class NioTcpService extends NioTcpBase {

	public static void main(String[] args) throws Exception {
		try(ServerSocketChannel ssc = ServerSocketChannel.open();
				ServerSocket serverSocket = ssc.socket()){
			//绑定要监听的端口
			serverSocket.bind(new InetSocketAddress(SERVER_IP, SERVER_PORT));
			ssc.configureBlocking(false);
			Selector selector = Selector.open();
			//注册感兴趣的连接建立事件
			ssc.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("Server 启动监听...");
			while(true){
				int nKeys = selector.select();
				if(nKeys != 0){
					Set<SelectionKey> keys = selector.selectedKeys();
//					for(SelectionKey key : keys){
					Iterator<SelectionKey> item = keys.iterator();
					while(item.hasNext()){
						System.out.println("...");
						SelectionKey key = item.next();
						if(key.isAcceptable()){
							System.out.println("Server 接受连接...");
							ServerSocketChannel server = (ServerSocketChannel) key.channel();
							try {
								SocketChannel channel = server.accept();
								if(channel == null){
									return;
								}
								
								channel.configureBlocking(false);
								channel.finishConnect();
								item.remove();
								String ret = "我是Server,现在已经完成连接!";
								channel.write(ByteBuffer.wrap(ret.getBytes(Charset.forName("UTF-8"))));
								//注册可读事件
								channel.register(selector, SelectionKey.OP_READ);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}else if(key.isReadable()){//有流可以读
							System.out.println("Server读取字节流...");
							ByteBuffer buffe = ByteBuffer.allocate(1024);
							SocketChannel sc = (SocketChannel) key.channel();
							int readBytes = 0;
							try{
								int ret = 0;
								try{
									//读取目前可读的流，sc.read返回的为成功复制到bytebuffer中的字节数，此步骤为阻塞操作，值为0；
									//当已经是流的结尾时，返回-1
									while((ret = sc.read(buffe)) > 0){
										readBytes += ret;
									}
								}finally {
									buffe.flip();
								}
								System.out.println("Server 读到的数据为：" + new String(buffe.array(),Charset.forName("UTF-8")));
							}finally{
								if(buffe != null){
									buffe.clear();
								}
							}
							item.remove();
							String data = " 你好，我是Server" + Calendar.getInstance().getTimeInMillis();
							System.out.println("Server 读取完毕，开始发送数据：" + data);
							sc.write(ByteBuffer.wrap(data.getBytes(Charset.forName("UTF-8"))));
							sc.register(selector, SelectionKey.OP_READ);
						}else if(key.isWritable()){//可写入流
							System.out.println("Server写入字节流...");
							//取消OP_WRITE事件的注册
							key.interestOps(key.interestOps() & (~SelectionKey.OP_WRITE));
							SocketChannel sc = (SocketChannel) key.channel();
							//次步为阻塞操作，直到写入操作系统发送缓冲区或网络IO出现异常，返回的为成功写入的字节数，
							//当操作系统的发送缓冲区已满，此处会返回0
							int writtenedSize = sc.write(ByteBuffer.wrap("我是Server，欢迎欢迎！".getBytes()));
							//如未写入，则继续注册感兴趣的OP_WRITE
							if(writtenedSize == 0){
								key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
							}
						}
					}
//					selector.selectedKeys().clear();
				}
				Thread.sleep(3000);
			}
			
			
		}
		
	}
}
