package tk.codecube;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import tk.codecube.common.dao.DepartmentDao;
import tk.codecube.common.dao.RoleDao;
import tk.codecube.common.dao.UserDao;
import tk.codecube.common.entity.Department;
import tk.codecube.common.entity.Role;
import tk.codecube.common.entity.User;
import tk.codecube.config.JpaConfig;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration
@ContextConfiguration(classes = {JpaConfig.class})
@PropertySource("application.properties")
public class MySQLTest {
    private static Logger logger = LoggerFactory.getLogger(JpaConfig.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private DepartmentDao departmentDao;

    @Before
    public void initData(){
        userDao.deleteAll();
        roleDao.deleteAll();
        departmentDao.deleteAll();

        Department d1 = new Department();
        d1.setName("开发部");
        departmentDao.save(d1);
        Assert.notNull(d1.getId(),"部门信息保存失败!");

        Role r1 = new Role();
        r1.setName("admin");
        roleDao.save(r1);
        Assert.notNull(r1.getId(),"角色保存失败！");

        Role r2 = new Role();
        r2.setName("model");
        roleDao.save(r2);
        Assert.notNull(r2.getId(),"角色保存失败！");

        User user = new User();
        user.setName("user");
        user.setCreatedate(new Date());
        user.setDepartment(d1);
        List<Role> roles = roleDao.findAll();
        Assert.notNull(roles,"角色表为空！");
        user.setRoles(roles);

        userDao.save(user);
        Assert.notNull(user.getId(),"保存用户信息失败！");

    }

    @Test
    public void findPage(){
        Pageable pageable = PageRequest.of(0,10,Sort.Direction.ASC,"id");
//        Pageable pageable = new PageRequest(0,10,new Sort(Sort.Direction.ASC,"id"));
        Page<User> page = userDao.findAll(pageable);

        Assert.notNull(page,"没有查询到用户信息！");
        for(User u : page.getContent()){

            logger.info("====user==== user  name:{},department name:{},role name:{}",u.getName(),u.getDepartment().getName(),u.getRoles().get(0).getName());
        }
    }
}
