package proxy;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description 代理服务器工具类
 * @Author Amiy
 * @Date 2018/9/28 11:44
 **/
public class ProxyUtils {

    private static final Logger logger = LoggerFactory.getLogger(ProxyUtils.class);
    private static final Pattern headerPattern = Pattern.compile("(\\S+): (.*)");

    private static final ExecutorService executorService = Executors.newFixedThreadPool(20);

    /**
     * @Description 从输入流中读取Header
     * @Param [inputStream]
     * @return org.apache.http.Header[]
     * @Author Aimy
     * @Date
     **/
    public static Header[] readHeaders(InputStream inputStream) throws IOException {
        List<Header> headers = new ArrayList<Header>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        logger.info("读取header--开始");
        while (reader.ready()){
            String line = reader.readLine();
            logger.info(line);
            String[] datas = line.split(":");
            if(datas.length >= 2){
                //第一个:之前的部分为key，剩余部分为Value
                headers.add(new BasicHeader(datas[0], StringUtils.substringAfter(line,":")));
            }
        }
        logger.info("读取header--结束");

        return headers.toArray(new Header[headers.size()]);
    }

    /**
     * @Description 获取服务器的socket
     * @Param [headers]
     * @return java.net.Socket
     * @Author Aimy
     * @Date
     **/
    public static Socket getServiceSocket(Header[] headers) throws Exception{
        logger.info(String.format("获取目标服务器的Socket"));
        Socket socket = null;
        for(Header header : headers){
            if(header.getName().equals(HttpHeaders.HOST)){

                String host = header.getValue().trim();
                String portStr = StringUtils.substringAfter(host,":").trim();
                String hostName = StringUtils.substringBefore(host,":").trim();
                InetAddress ip = InetAddress.getByName(hostName);
                portStr = StringUtils.isBlank(portStr) ? "80" : portStr;
                logger.info(String.format("Host:%s, HostName:%s, Port:%s",host,hostName,portStr));

                socket = new Socket(ip,Integer.parseInt(portStr));
            }
        }
        return socket;
    }

    /**
     * @Description 将输入流的数据写入到输出流中
     * @Param [inputStream, outputStream]
     * @return void
     * @Author Aimy
     * @Date
     **/
    public static boolean writeFromInputStreame(InputStream inputStream, OutputStream outputStream) throws Exception{
        byte[] buffer = new byte[2048];
        int len = 0;
        try {
            while((len = inputStream.read(buffer)) >= 0){
                outputStream.write(buffer,0,len);
            }
            outputStream.flush();
            if(len < 0){
                return true;
            }

        }catch (Exception e){
            logger.error("读取数据失败...",e);
            return true;
        }
        return false;
    }

    /**
     * @Description 读取全部数据
     * @Param [inputStream]
     * @return java.lang.String
     * @Author Aimy
     * @Date  
     **/
    public static String readRequest(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder ret = new StringBuilder();
        logger.info("读取socket--开始");
        while (reader.ready()){
            String line = reader.readLine();
            logger.info(line);
            ret.append(line).append("\n");
        }
        logger.info("读取header--结束");
        return ret.toString();
    }

    /**
     * @Description 读取header
     * @Param [requestInfo]
     * @return org.apache.http.Header[]
     * @Author Aimy
     * @Date  
     **/
    public static Header[] readHeaders(String requestInfo) {
        List<Header> headers = new ArrayList<Header>();
        Matcher matcher = headerPattern.matcher(requestInfo);
        while (matcher.find()){
            headers.add(new BasicHeader(matcher.group(1),matcher.group(2)));
        }
        return headers.toArray(new Header[headers.size()]);
    }

    public static ExecutorService getThreadPool(){
        return executorService;
    }
}
