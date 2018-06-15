package ml.idream;

import ml.idream.config.JpaConfig;
import ml.idream.sys.department.SysDepartment;
import ml.idream.sys.department.SysDepartmentService;
import ml.idream.sys.role.SysRole;
import ml.idream.sys.role.SysRoleService;
import ml.idream.sys.user.SysUser;
import ml.idream.sys.user.SysUserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration
@ContextConfiguration(classes = {JpaConfig.class})
@PropertySource("application.properties")
public class MySQLTest {
    private static Logger logger = LoggerFactory.getLogger(JpaConfig.class);

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysDepartmentService sysDepartmentService;

    @Before
    public void initData(){
        sysUserService.deleteAll();
        sysRoleService.deleteAll();
        sysDepartmentService.deleteAll();

        SysDepartment d1 = new SysDepartment();
        d1.setName("开发部");
        sysDepartmentService.save(d1);
        Assert.notNull(d1.getId(),"部门信息保存失败!");

        SysRole r1 = new SysRole();
        r1.setName("ROLE_ADMIN");
        sysRoleService.save(r1);
        Assert.notNull(r1.getId(),"角色保存失败！");

        SysRole r2 = new SysRole();
        r2.setName("ROLE_USER");
        sysRoleService.save(r2);
        Assert.notNull(r2.getId(),"角色保存失败！");

        //admin:User1
        SysUser user = new SysUser();
        user.setName("admin");
        user.setCreatedate(new Date());
        user.setSysDepartment(d1);
        user.setPassword("1");
//        List<SysRole> roles = roleDao.findAll();
//        Assert.notNull(roles,"角色表为空！");
        List<SysRole> roles = new ArrayList<SysRole>();
        roles.add(r1);
        user.setRoles(roles);

        sysUserService.save(user);
        Assert.notNull(user.getId(),"保存用户信息失败！");

        //user:User2
        SysUser user2 = new SysUser();
        user2.setName("user2");
        user2.setCreatedate(new Date());
        user2.setSysDepartment(d1);
        user2.setPassword("2");
//        List<SysRole> roles = roleDao.findAll();
//        Assert.notNull(roles,"角色表为空！");
        List<SysRole> roles2 = new ArrayList<SysRole>();
        roles2.add(r2);
        user2.setRoles(roles2);

        sysUserService.save(user);
        Assert.notNull(user.getId(),"保存用户信息失败！");

    }

    @Test
    public void findPage(){
//        Pageable pageable = PageRequest.of(0,10,Sort.Direction.ASC,"id");
////        Pageable pageable = new PageRequest(0,10,new Sort(Sort.Direction.ASC,"id"));
//        Page<SysUser> page = userDao.findAll(pageable);
//
//        Assert.notNull(page,"没有查询到用户信息！");
//        for(SysUser u : page.getContent()){
//
//            logger.info("====user==== user  name:{},department name:{},role name:{}",u.getName(),u.getSysDepartment().getName(),u.getRoles().get(0).getName());
//        }
    }
}
