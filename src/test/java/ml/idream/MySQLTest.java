package ml.idream;

import ml.idream.blog.BlogService;
import ml.idream.manage.sys.department.SysDepartment;
import ml.idream.manage.sys.department.SysDepartmentService;
import ml.idream.manage.sys.permission.SysPermission;
import ml.idream.manage.sys.permission.SysPermissionService;
import ml.idream.manage.sys.role.SysRole;
import ml.idream.manage.sys.role.SysRoleService;
import ml.idream.manage.sys.user.SysUser;
import ml.idream.manage.sys.user.SysUserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration
@EnableAsync
@ComponentScan(basePackages = "ml.idream")
public class MySQLTest {

    private static Logger logger = LoggerFactory.getLogger(MySQLTest.class);

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysDepartmentService sysDepartmentService;
    @Autowired
    private SysPermissionService sysPermissionService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private BlogService blogService;


    private boolean hasInit = true;

    @Before
    public void initData(){

        if(hasInit){
            return;
        }

        sysUserService.deleteAll();
        sysRoleService.deleteAll();
        sysDepartmentService.deleteAll();
        sysPermissionService.deleteAll();

        /*部门*/
        SysDepartment d1 = new SysDepartment();
        d1.setName("开发部");
        sysDepartmentService.save(d1);
        Assert.notNull(d1.getId(),"部门信息保存失败!");

        /*权限*/
        SysPermission permissionManage = new SysPermission();
        permissionManage.setName("管理员");
        permissionManage.setUrlPattern("/manage");
        permissionManage.setDescription("管理员权限");
        sysPermissionService.save(permissionManage);
        SysPermission permissionStore = new SysPermission();
        permissionStore.setName("商店");
        permissionStore.setUrlPattern("/store");
        permissionStore.setDescription("商店权限");
        sysPermissionService.save(permissionStore);
        SysPermission permissionBlog = new SysPermission();
        permissionBlog.setName("博客");
        permissionBlog.setUrlPattern("/blog");
        permissionBlog.setDescription("博客权限");
        sysPermissionService.save(permissionBlog);

        /*管理员权限集合*/
        List<SysPermission> adminPermission = new ArrayList<SysPermission>();
        adminPermission.add(permissionManage);
        adminPermission.add(permissionStore);
        adminPermission.add(permissionBlog);
        /*用户权限集合*/
        List<SysPermission> userPermission = new ArrayList<SysPermission>();
        userPermission.add(permissionStore);
        userPermission.add(permissionBlog);

        /*角色*/
        SysRole r1 = new SysRole();
        r1.setName("ROLE_ADMIN");
        r1.setPermissions(adminPermission);
        sysRoleService.save(r1);
        Assert.notNull(r1.getId(),"角色保存失败！");

        SysRole r2 = new SysRole();
        r2.setName("ROLE_USER");
        r2.setPermissions(userPermission);
        sysRoleService.save(r2);
        Assert.notNull(r2.getId(),"角色保存失败！");

        /*用户*/
        //admin:User1
        SysUser user = new SysUser();
        user.setName("admin");
        user.setCreatedate(new Date());
        user.setSysDepartment(d1);
        user.setPassword(passwordEncoder.encode("1"));
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
        user2.setPassword(passwordEncoder.encode("1"));
//        List<SysRole> roles = roleDao.findAll();
//        Assert.notNull(roles,"角色表为空！");
        List<SysRole> roles2 = new ArrayList<SysRole>();
        roles2.add(r2);
        user2.setRoles(roles2);

        sysUserService.save(user2);
        Assert.notNull(user.getId(),"保存用户信息失败！");

    }

    @Test
    public void findPage(){
        System.out.println("----初始化成功----");
    }


    @Test
    public void testAsync() throws InterruptedException, ExecutionException {
        System.out.println( Thread.currentThread().getName() + " --- 开始 ---");
        Future<Map<String,Object>> ret = blogService.printMsg("哈哈");
        for (Map.Entry<String,Object> entry : ret.get().entrySet()){
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        System.out.println( Thread.currentThread().getName() + " --- 结束 ---");

    }
}
