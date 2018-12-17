package ml.idream.qq.common;

/**
 * @Description 二维码状态
 * @Author Aimy
 * @Date 2018/12/17 14:20
 **/
public enum QRCodeStatus {
    NONE(-1,"未获取二维码"),
    EFFECTIVITY(66,"有效"),
    UNEFFECTIVITY(65,"无效"),
    CHECKING(60,"校验中"),
    LOGIN(0,"登陆");

    private Integer value;
    private String label;

    QRCodeStatus(Integer value, String label) {
        this.value = value;
        this.label = label;
    }
}
