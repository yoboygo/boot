package ml.idream.qq;

import org.apache.http.Header;

import java.util.regex.Pattern;

/**
 * @Description
 * @Author Aimy
 * @Date 2018/12/7 16:05
 **/
public class BaseResponse {

    private Header[] headers;

    private Pattern patternCookie = Pattern.compile("(\\w+)=(\\S+?);");


}
