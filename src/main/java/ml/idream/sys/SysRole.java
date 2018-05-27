package ml.idream.sys;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/*
* 角色表
*
* */
@Entity
@Table( name = "sys_role")
public class SysRole implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;

    @ManyToMany(cascade = {},fetch = FetchType.EAGER)
    @JoinTable(name= "sys_permission_role",joinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")},inverseJoinColumns = {@JoinColumn(name = "permission_id",referencedColumnName = "id")})
    private List<SysPermission> permissions;

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
