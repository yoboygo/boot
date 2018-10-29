package ml.idream.distribute.bio.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Calendar;

/**
 * TCP/IP BIO 客户端
 * @page 4
 * @author Aimy
 *
 */
public class BioTcpClient extends BioTcpBase{

	public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
		try(Socket socket = new Socket(SERVER_IP,SERVER_PORT);
				//创建服务器返回流的BufferedReader
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), Charset.defaultCharset()));
				//创建向服务器写入的流PrintWriter
				PrintWriter out = new PrintWriter(socket.getOutputStream(),true)){
				
				socket.setSoTimeout(Integer.MAX_VALUE);
			
				//向服务器发送字符串信息，此处即使写失败也不会抛出异常信息，并且一直阻塞到写操作系统或网络IO出现异常为止。
				String msg = "你好！" + Calendar.getInstance().getTimeInMillis();
				System.out.println("Client向服务器发送数据-->" + msg);
				out.println(msg);
				//阻塞读取服务端的返回信息，一下代码会阻塞到服务端返回信息或网络IO出现异常为止，如果希望在超过一段时间后就不阻塞了，那么要在创建Socket对象后调用socket.setSoTimeout(一毫秒为单位的超时时间)
				String response = in.readLine();
				System.out.println("服务器返回消息：" + response);
		}
		
	}
}
