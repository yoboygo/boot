package ml.idream.sys.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import ml.idream.sys.department.SysDepartment;
import ml.idream.sys.role.SysRole;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
/*
* User 表
* */
@Entity
@Table(name = "sys_user")
public class SysUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String password;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdate = Calendar.getInstance().getTime();

    @ManyToOne
    @JoinColumn(name = "did")
    @JsonBackReference
    private SysDepartment sysDepartment;

    @ManyToMany(cascade = {},fetch = FetchType.EAGER)
    @JoinTable(name = "sys_user_role",joinColumns = {@JoinColumn(name = "user_id",referencedColumnName = "id")},inverseJoinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")})
    private List<SysRole> roles;

    public SysUser(){

    }

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

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public SysDepartment getSysDepartment() {
        return sysDepartment;
    }

    public void setSysDepartment(SysDepartment sysDepartment) {
        this.sysDepartment = sysDepartment;
    }

    public List<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(List<SysRole> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /*建造者*/
    public static class Builder{
        private SysUser user;

        public Builder() {
            this.user = new SysUser();
        }

        public Builder setName(String userName){
            user.setName(userName);
            return this;
        }
        public Builder setPassWord(String passWord){
            user.setPassword(passWord);
            return this;
        }
        public SysUser build(){
            return user;
        }
    }
}
