package ml.idream.autocode;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description 解析工具类
 * @Author Aimy
 * @Date 2018/10/11 17:25
 **/
public class CodeBaseUtils {

    //从url中解析出Controller
    private static final Pattern controllerPattern = Pattern.compile("/(\\w+)/\\w+\\.action");
    //从url中解析出method
    private static final Pattern methodPattern = Pattern.compile("/(\\w+)\\.action");

    /**
     * @Description 从URL中解析出Controller的类名
     * @Param [url]
     * @return java.lang.String
     * @Author Aimy
     * @Date  
     **/
    public static String controllerPrasser(String url){
        Matcher controllerMatcher = controllerPattern.matcher(url);
        if (controllerMatcher.find()){
            String controller = controllerMatcher.group(1);
            char firstCharcter = controller.charAt(0);
            if(Character.isUpperCase(firstCharcter)){
                return controller;
            }else{
                return controller.replaceFirst(firstCharcter+"",(firstCharcter + "").toUpperCase());
            }
        }
        return null;
    }
    /**
     * @Description 从URL中解析出Controller的类名
     * @Param [url]
     * @return java.lang.String
     * @Author Aimy
     * @Date
     **/
    public static String methodPattern(String url){

        Matcher methodMatcher = methodPattern.matcher(url);
        if (methodMatcher.find()){
            return methodMatcher.group(1);
        }
        return null;
    }

    /**
     * @Description 首字母大写
     * @Param [source]
     * @return java.lang.String
     * @Author Aimy
     * @Date  
     **/
    public static String upperCaseFirstChatar(String source){
        String firstChar = ((Character)source.charAt(0)).toString();
        return source.replaceFirst(firstChar,firstChar.toUpperCase());
    }

}
