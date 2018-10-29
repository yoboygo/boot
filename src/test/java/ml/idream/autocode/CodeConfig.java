package ml.idream.autocode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 配置类
 * @Author Aimy
 * @Date 2018/10/11 19:05
 **/
public class CodeConfig {

    private String outputPath = "";

    private String paramPackage = "";
    private String resultPackage = "";

    private String controllerPackage = "";
    private String servicePackage = "";

    //参数中的保留字，在生成类的时候排除里面的字段
    private List<String> paramStayKey = new ArrayList<String>();
    {
        paramStayKey.add("hmac");
        paramStayKey.add("clientId");
        paramStayKey.add("pageCurrent");
        paramStayKey.add("pageSize");
    }
    //返回值中的保留字，生成类时排除里面的字段
    private List<String> responseStayKey = new ArrayList<String>();
    {
        responseStayKey.add("hmac");
        responseStayKey.add("status");
        responseStayKey.add("code");
        responseStayKey.add("msg");
    }


    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public String getParamPackage() {
        return paramPackage;
    }

    public void setParamPackage(String paramPackage) {
        this.paramPackage = paramPackage;
    }

    public String getResultPackage() {
        return resultPackage;
    }

    public void setResultPackage(String resultPackage) {
        this.resultPackage = resultPackage;
    }

    public String getControllerPackage() {
        return controllerPackage;
    }

    public void setControllerPackage(String controllerPackage) {
        this.controllerPackage = controllerPackage;
    }

    public String getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(String servicePackage) {
        this.servicePackage = servicePackage;
    }

    public List<String> getParamStayKey() {
        return paramStayKey;
    }

    public void setParamStayKey(List<String> paramStayKey) {
        this.paramStayKey = paramStayKey;
    }

    public List<String> getResponseStayKey() {
        return responseStayKey;
    }

    public void setResponseStayKey(List<String> responseStayKey) {
        this.responseStayKey = responseStayKey;
    }
}
