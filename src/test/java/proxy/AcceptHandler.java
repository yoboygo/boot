package proxy;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @Description 处理连接请求
 * @Author Aimy
 * @Date 2018/9/29 14:28
 **/
public class AcceptHandler implements Runnable{

    private InputStream clientInputStream;
    private OutputStream clientOutputStream;

    private InputStream serviceInputStream;
    private OutputStream serviceOutputStream;

    private Socket clientSocket;

    private static final Logger logger = LoggerFactory.getLogger(AcceptHandler.class);

    public AcceptHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        logger.info("代理服务器开始运行");
        try {
            clientSocket.setSoTimeout(50);
            clientInputStream = clientSocket.getInputStream();
            clientOutputStream = clientSocket.getOutputStream();
            //读取浏览器的信息
            String requestInfo = ProxyUtils.readRequest(clientInputStream);
            logger.info("浏览器的请求信息为：" + requestInfo);
            Header[] headers = ProxyUtils.readHeaders(requestInfo);
            //建立代理服务器与目标服务器的连接
            Socket serviceSocket = ProxyUtils.getServiceSocket(headers);
            serviceSocket.setKeepAlive(true);
            serviceSocket.setSoTimeout(3000);
            serviceInputStream = serviceSocket.getInputStream();
            serviceOutputStream = serviceSocket.getOutputStream();

            //将Header转发到目标服务器，并且将返回的数据写回到Client
            String firstLine = StringUtils.substringBefore(requestInfo,"\n");
            serviceOutputStream.write(firstLine.getBytes("UTF-8"));
            serviceOutputStream.write(" ".getBytes());
            serviceOutputStream.write(requestInfo.replace(firstLine,"").getBytes("UTF-8"));
            serviceOutputStream.write(" ".getBytes());

//            serviceOutputStream.flush();
            //将服务器返回的数据转发到浏览器
            pipe(clientInputStream,clientOutputStream,serviceInputStream,serviceOutputStream);
            logger.info(firstLine + "--->执行结束");
        } catch (Exception e) {
            logger.error("代理服务器异常",e);
        }
    }

    /**
     * @Description 转发数据
     * @Param [clientInputStream, clientOutputStream, serviceInputStream, serviceOutputStream]
     * @return void
     * @Author Aimy
     * @Date
     **/
    void pipe(InputStream clientInputStream,OutputStream clientOutputStream,InputStream serviceInputStream,OutputStream serviceOutputStream) throws Exception {

        while (true){
            //将服务器返回的数据转发到浏览器
            logger.info("将服务器返回的数据转发到浏览器...");
            boolean breakFlag = ProxyUtils.writeFromInputStreame(serviceInputStream,clientOutputStream);
            if(breakFlag){
                break;
            }
            logger.info("将浏览器的请求转发到服务器...");
            //将浏览器的请求转发到服务器
            breakFlag = ProxyUtils.writeFromInputStreame(clientInputStream,serviceOutputStream);
            if(breakFlag){
                break;
            }
        }

    }
}
