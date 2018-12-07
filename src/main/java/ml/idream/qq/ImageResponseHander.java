package ml.idream.qq;


import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Calendar;

/**
 * @Description 获取到的图片写入到指定目录
 * @Author Aimy
 * @Date 2018/12/7 15:01
 **/
public class ImageResponseHander implements ResponseHandler<ImageResponseBody> {

    private static final Logger logger = LoggerFactory.getLogger(ImageResponseHander.class);

    private CookieStore cookieStore;
    private String path;

    public ImageResponseHander(String path,CookieStore cookieStore) {
        this.path = path;
        this.cookieStore = cookieStore;
    }

    @Override
    public ImageResponseBody handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        ImageResponseBody result = new ImageResponseBody(cookieStore);
        int status = response.getStatusLine().getStatusCode();
        String fileName = Calendar.getInstance().getTimeInMillis() + ".png";
        String filePath = path + "\\" + fileName;

        logger.info("filePath:{}",filePath);
        result.setFilePath(filePath);
        result.setHeaders(response.getAllHeaders());

        try(InputStream inputStream = response.getEntity().getContent();
            OutputStream out = new FileOutputStream(filePath);){
            if(status >= 200 && status < 300){
                byte[] datas = new byte[1024];
                int len = 0;
                while((len = inputStream.read(datas)) != -1){
                    out.write(datas,0,len);
                }
                return result;
            }else{
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        }
    }
}
