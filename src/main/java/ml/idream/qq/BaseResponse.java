package ml.idream.qq;

import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description
 * @Author Aimy
 * @Date 2018/12/7 16:05
 **/
public class BaseResponse {

    private CookieStore cookieStore;
    private Header[] headers;

    private Pattern patternCookie = Pattern.compile("(\\w+)=(\\S+?);");

    public BaseResponse(CookieStore cookieStore) {
        this.cookieStore = cookieStore;
    }

    public Header[] getHeaders() {
        return headers;
    }

    public void setHeaders(Header[] headers) {
        this.headers = headers;

        //处理请求中的Cookies
        for(Header header : headers){
            if(header.getName().equals("Set-Cookie")){
                BasicClientCookie cookie = new BasicClientCookie("name","value");
                Matcher matcher = patternCookie.matcher(header.getValue());
                while (matcher.find()){
                    String key = matcher.group(1);
                    if(key.equals("Domain")){
                        cookie.setDomain(matcher.group(2));
                        continue;
                    }
                    if(key.equals("Path")){
                        cookie.setPath(matcher.group(2));
                        continue;
                    }
                    cookie.setAttribute(key,matcher.group(2));

                }
                getCookieStore().addCookie(cookie);
            }
        }
    }

    public CookieStore getCookieStore() {
        return cookieStore;
    }

    public void setCookieStore(CookieStore cookieStore) {
        this.cookieStore = cookieStore;
    }
}
