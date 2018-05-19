package tk.codecube.common.entity;

import tk.codecube.base.AbstractBaseModel;

import javax.persistence.*;
/*
* 角色表
*
* */
@Entity
@Table( name = "boot_role")
public class Role extends AbstractBaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

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
}
