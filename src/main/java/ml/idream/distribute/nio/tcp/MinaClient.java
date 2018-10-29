package ml.idream.distribute.nio.tcp;

import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

/**
 * @page 11 
 * @author Aimy
 *
 */
public class MinaClient extends NioTcpBase{

	public static void main(String[] args) {
		
		//创建一个线程池大小为CPU核数+1的SocketConnector对象
		SocketConnector ioConnector =  new NioSocketConnector(Runtime.getRuntime().availableProcessors() + 1);
		//设置TCP NoDelay为true
		ioConnector.getSessionConfig().setTcpNoDelay(true);
		//增加一个将发送对象进行序列化以及接收字节流进行反序列化的类只filter Chain
		ioConnector.getFilterChain().addLast("StringSerialize", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
		//IoHandler的实现，以便当mina简历连接，接收到消息后通知应用
		IoHandler handler = new IoHandlerAdapter(){
			public void messageReceived(IoSession session,Object message){
				System.out.println(message);
			}
		};
		//异步建立连接
		ioConnector.setHandler(handler);
		ConnectFuture connectFutuer = ioConnector.connect(new InetSocketAddress(SERVER_IP,SERVER_PORT));
		ioConnector.setConnectTimeoutMillis(5000);
		connectFutuer.awaitUninterruptibly(3000);
		IoSession session = connectFutuer.getSession();
		//发送对象
		session.write("你好，我是MinaClient~");
		
	}
}
