package ml.idream.common;

/**
 * @Description 返回点枚举类
 * @Author Aimy
 * @Date 2018/12/18 11:39
 **/
public enum DreamResponseCode {

    SUCCESS(0,"成功"),FAULT(1000,"失败");

    private int value;
    private String flag;

    DreamResponseCode(int value, String flag) {
        this.value = value;
        this.flag = flag;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }}
