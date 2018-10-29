package ml.idream.distribute.nio.udp;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

/**
 * UDP NIO
 * @page 9 
 * @author Aimy
 *
 */
public class NioUdpClient {

	public static void main(String[] args) throws IOException {
		DatagramChannel receiveChannel = DatagramChannel.open();
		receiveChannel.configureBlocking(false);
		DatagramSocket socket = receiveChannel.socket();
		socket.bind(new InetSocketAddress(999));
		Selector selector = Selector.open();
		receiveChannel.register(selector, SelectionKey.OP_READ);
		//之后即可才去和TCP/IP + NIO中对selector遍历一样的方式进行流信息的读取。
		
	}

}
