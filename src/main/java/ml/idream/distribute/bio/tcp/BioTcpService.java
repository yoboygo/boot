package ml.idream.distribute.bio.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * TCP/IP BIO 服务端
 * @page 4
 * @author Aimy
 *
 */
public class BioTcpService extends BioTcpBase{
	
	public static void main(String[] args) throws IOException {
		
		//接受客户端简历连接的请求，并返回Socket对象，以便和客户端进行交互，交互的方式和客户端相同
		//也是通过socket.getInputStream 和 socket.getOutputStream来进行读写操作，此方法
		//会一直阻塞到有客户端发送建立连接的请求，如果希望此方法最多阻塞一定时间，则要在创建ServerSocket
		//时调用setSoTimeout(以毫秒为单位)
		try(ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
				Socket socket = serverSocket.accept();
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(),Charset.defaultCharset()));
				PrintWriter out = new PrintWriter(socket.getOutputStream(),true)){
			
				String msg = "欢迎连接！";
				System.out.println("Server 发送欢迎信息：" + msg);
				out.println(msg);
				String retMsg = in.readLine();
				System.out.println("Server收到信息：" + retMsg);
		}
		
	}
}
