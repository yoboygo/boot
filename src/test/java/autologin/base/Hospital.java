package autologin.base;

/**
 * @Description 医院实体
 * @Author Aimy
 * @Date 2018/9/27 15:37
 **/
public class Hospital {

    private String code;
    private String name;
    private String bookTime;
    private String levealLabel;
    private String tel;
    private String address;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBookTime() {
        return bookTime;
    }

    public void setBookTime(String bookTime) {
        this.bookTime = bookTime;
    }

    public String getLevealLabel() {
        return levealLabel;
    }

    public void setLevealLabel(String levealLabel) {
        this.levealLabel = levealLabel;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
