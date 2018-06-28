package ml.idream.global;

import java.util.List;

/*
* 返回Bean
* */
public class ResBean {

    private String code = GlobalConst.CODE_SUCCESS;
    private String msg = GlobalConst.MSG_SUCCESS;
    private List<?> datas;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<?> getDatas() {
        return datas;
    }

    public void setDatas(List<?> datas) {
        this.datas = datas;
    }
}
