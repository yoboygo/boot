package ml.idream.manage.sys.user;

import ml.idream.global.GlobalConst;
import ml.idream.manage.sys.department.SysDepartment;
import ml.idream.manage.sys.role.SysRole;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
/*
* User 表
* */
//sys_user
public class SysUser implements Serializable {
    private Long id;

    private String name;

    private String password;

    private String email;

    private Date createdate = Calendar.getInstance().getTime();

    private SysDepartment sysDepartment;

    private List<SysRole> roles = new ArrayList<SysRole>();

    private String delFlag = GlobalConst.FLAG_UNDEL;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
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
        public Builder setEmail(String email){
            user.setEmail(email);
            return this;
        }
        public SysUser build(){
            return user;
        }
    }

}