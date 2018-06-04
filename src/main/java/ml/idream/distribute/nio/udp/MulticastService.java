package ml.idream.distribute.nio.udp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * 多播服务
 * @author songjianlong
 *
 */
public class MulticastService {

	public static void main(String[] args) throws IOException {
		InetAddress groupAddress = InetAddress.getByName("127.0.0.1");
		MulticastSocket server = new MulticastSocket(999);
		//加入组播，如地址为非洲组播地址，则抛出IOExceptio,当已经不希望再发送数据到
		//组播地址，或不希望再读取数据时，可调用server.leaveGroup（组播地址）
		server.joinGroup(groupAddress);
		MulticastSocket client = new MulticastSocket();
		client.joinGroup(groupAddress);
		//之后则可和UDP/IP + BIO一样通过receive和send方法来进行读写操作
	}
}
