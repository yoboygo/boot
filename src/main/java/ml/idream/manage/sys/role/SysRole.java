package ml.idream.manage.sys.role;

import ml.idream.global.GlobalConst;
import ml.idream.manage.sys.permission.SysPermission;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
* 角色表
*
* */
//sys_role
public class SysRole implements Serializable {
    private Long id;
    private String name;
    private String delFlag = GlobalConst.FLAG_UNDEL;

    private List<SysPermission> permissions = new ArrayList<SysPermission>();

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

    public List<SysPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<SysPermission> permissions) {
        this.permissions = permissions;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
