package ml.idream.qq.handler;


import ml.idream.qq.entity.ImageResponseBody;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

/**
 * @Description 获取到的图片写入到指定目录
 * @Author Aimy
 * @Date 2018/12/7 15:01
 **/
public class ImageResponseHander implements ResponseHandler<ImageResponseBody> {

    private static final Logger logger = LoggerFactory.getLogger(ImageResponseHander.class);

    private String path;
    private String filePrefix;

    private String fileNameKey;
    private boolean overWrite = true;

    public ImageResponseHander(String path) {
        this.path = path;
    }

    public ImageResponseHander(String path, String fileNameKey, boolean overWrite) {
        this.path = path;
        this.fileNameKey = fileNameKey;
        this.overWrite = overWrite;
    }

    public ImageResponseHander(String path, String filePrefix) {
        this.path = path;
        this.filePrefix = filePrefix;
    }

    @Override
    public ImageResponseBody handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        ImageResponseBody result = new ImageResponseBody();
        int status = response.getStatusLine().getStatusCode();
        String fileName = (StringUtils.isBlank(filePrefix) ? "" : (filePrefix + "_")) + Calendar.getInstance().getTimeInMillis() + ".png";
        String filePath = path + "\\" + fileName;

        logger.info("filePath:【{}】",filePath);
        result.setFilePath(filePath);

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFilePrefix() {
        return filePrefix;
    }

    public void setFilePrefix(String filePrefix) {
        this.filePrefix = filePrefix;
    }

    public String getFileNameKey() {
        return fileNameKey;
    }

    public void setFileNameKey(String fileNameKey) {
        this.fileNameKey = fileNameKey;
    }

    public boolean isOverWrite() {
        return overWrite;
    }

    public void setOverWrite(boolean overWrite) {
        this.overWrite = overWrite;
    }
}
