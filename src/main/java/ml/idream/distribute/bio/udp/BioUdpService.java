package ml.idream.distribute.bio.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * NIO UDP实现
 * @page 8
 * @author Aimy
 *
 */
public class BioUdpService extends AbstractBioUdpBase{

	public static void main(String[] args) throws IOException {
		byte[] buffer = new byte[1024];
		String data = "udpClient2";
		//由于UDP/IP是无连接的，如果希望双向通信，就必须启动一个监听端口，承担服务器的职责，如果不能
		//绑定到指定端口，则抛出SocketException
		InetSocketAddress socketAddress = new InetSocketAddress(SERVER_IP, SERVER_1_PORT);
		DatagramSocket serverSocket = new DatagramSocket(SERVER_2_PORT);
		DatagramPacket receivePacket = new DatagramPacket(buffer,buffer.length);
		DatagramSocket socket = new DatagramSocket();
		DatagramPacket packet = new DatagramPacket(data.getBytes(Charset.forName("UTF-8")), data.length(),socketAddress);
		//阻塞发送packet到指定的服务器和端口，当出现网络IO异常时抛出IOException,当连不上目标地址和端口时，抛出PortUnreachableException
		System.out.println("发送信息：" + data);
		socket.send(packet);
		//阻塞并同步读取流信息，如接收到的流信息比packet长度长,则删除更长的信息，可通过调用setSoTimeout
		//设置读取流的超时时间
		socket.setSoTimeout(Integer.MAX_VALUE);
		serverSocket.receive(receivePacket);
		System.out.println("接收到信息：" + new String(buffer,Charset.forName("UTF-8")));
	}

}
