package ml.idream.manage.sys.department;

import ml.idream.global.GlobalConst;

import java.io.Serializable;

//sys_deparment
public class SysDepartment implements Serializable {

    private Long id;

    private String name;

    private String delFlag = GlobalConst.FLAG_UNDEL;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
