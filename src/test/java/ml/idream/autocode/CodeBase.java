package ml.idream.autocode;

/**
 * @Description 所有基本信息
 * @Author Aimy
 * @Date 2018/10/11 16:00
 **/
public class CodeBase {

    private String url;
    private String controller;
    private String method;
    private String param;
    private String response;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
