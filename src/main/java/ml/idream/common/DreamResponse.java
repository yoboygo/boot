package ml.idream.common;

import java.io.Serializable;

/**
 * @Description 用于返回结果
 * @Author Aimy
 * @Date 2018/12/18 11:37
 **/
public class DreamResponse<T> implements Serializable {


    private int code;
    private String msg;

    private T data;

    public DreamResponse() {
    }

    public DreamResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <E> DreamResponse defaultSuccess(){
        return new DreamResponse<E>();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
