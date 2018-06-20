package ml.idream.sys.role;

import ml.idream.sys.permission.SysPermission;

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
}
