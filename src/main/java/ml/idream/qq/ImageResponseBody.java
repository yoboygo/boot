package ml.idream.qq;

import org.apache.http.client.CookieStore;

import javax.servlet.http.Cookie;

/**
 * @Description 返回结果
 * @Author Aimy
 * @Date 2018/12/7 16:03
 **/
public class ImageResponseBody extends BaseResponse {

    private String filePath;

    public ImageResponseBody(CookieStore cookieStore) {
        super(cookieStore);
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}
