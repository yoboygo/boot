package ml.idream.distribute.nio.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;



/**
 * TCP NIO
 * @page 5
 * @author songjianlong
 *
 */
public class NioTcpClient extends NioTcpBase{
	
	public static void main(String[] args) throws IOException, InterruptedException {
		try(SocketChannel socketChannel = SocketChannel.open();){
			//设置为非阻塞模式
			socketChannel.configureBlocking(false);
			InetSocketAddress socketAddress = new InetSocketAddress(SERVER_IP,SERVER_PORT);
			socketChannel.connect(socketAddress);
			Selector selector = Selector.open();
			//向channle注册selector以及感兴趣的连接事件
			socketChannel.register(selector, SelectionKey.OP_CONNECT);
			//阻塞至有感兴趣的IO事发生，或到达超时时间，如果希望一直等至有感兴趣的IO事件发生
			//可调用无参的select方法，如果希望不阻塞直接返回目前是否有感兴趣的事件发生，可调用
			//selectNow方法
			System.out.println("Client 启动监听...");
			while(true){
				System.out.println("...");
				int nKeys = selector.select();
				SelectionKey sKey = null;
				if(nKeys > 0){
					Set<SelectionKey> keys = selector.selectedKeys();
//					for(SelectionKey key : keys){
					Iterator<SelectionKey> item = keys.iterator();
					while(item.hasNext()){
						SelectionKey key = item.next();
						//对于发生连接的事件
						if(key.isConnectable()){
							System.out.println("Client建立连接...");
							SocketChannel sc = (SocketChannel) key.channel();
							sc.configureBlocking(false);
							//注册感兴趣的IO事件发生，通常不直接注册写事件，在发送缓冲区未满的情况下，一直是可写的，因此如注册了写
							//事件，而又不写数据，很容易造成CPU消耗100%现象
							sKey = sc.register(selector,SelectionKey.OP_READ);
							//完成连接的建立
							sc.finishConnect();
							item.remove();
							//写入数据
							sc.write(ByteBuffer.wrap("Client完成连接，开始发送数据...".getBytes(Charset.forName("UTF-8"))));
							sc.register(selector, SelectionKey.OP_READ);
						}else if(key.isReadable()){//有流可以读
							System.out.println("Client读取字节流...");
							ByteBuffer buffe = ByteBuffer.allocate(1024);
							SocketChannel sc = (SocketChannel) key.channel();
							//TODO read byte
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
								String response = new String(buffe.array(),Charset.forName("UTF-8"));
								System.out.println("Client 接收到数据：" + response);
								String datas = "你好，我是Client " + Calendar.getInstance().getTimeInMillis();
								sc.write(ByteBuffer.wrap(datas.getBytes(Charset.forName("UTF-8"))));
								//一定要将当前key remove掉，然后重新注册，否则不能重复调用
								item.remove();
								sc.register(selector, SelectionKey.OP_READ);
							}finally{
								if(buffe != null){
									buffe.clear();
								}
							}
							
						}else if(key.isWritable()){//可写入流
							System.out.println("Client写入字节流...");
							//取消OP_WRITE事件的注册
							key.interestOps(key.interestOps() & (~SelectionKey.OP_WRITE));
							SocketChannel sc = (SocketChannel) key.channel();
							//次步为阻塞操作，直到写入操作系统发送缓冲区或网络IO出现异常，返回的为成功写入的字节数，
							//当操作系统的发送缓冲区已满，此处会返回0
							int writtenedSize = sc.write(ByteBuffer.wrap("我是Client，你好!".getBytes()));
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
			//对于要写入的流，可直接调用channel.write来完成，只有写入未成功时才注册OP_WRITE事件
//			int wSize = socketChannel.write(ByteBuffer.wrap("直接写入".getBytes()));
//			if(wSize == 0){
//				key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
//			}
		}
	}

}
