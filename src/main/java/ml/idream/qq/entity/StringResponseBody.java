package ml.idream.qq.entity;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.http.StatusLine;

/**
 * @Description HTTP String 结果返回实体
 * @Author Aimy
 * @Date 2018/12/17 15:32
 **/
public class StringResponseBody {

    private StatusLine statusLine;
    private String value;

    public StatusLine getStatusLine() {
        return statusLine;
    }

    public void setStatusLine(StatusLine statusLine) {
        this.statusLine = statusLine;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
