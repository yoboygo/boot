package ml.idream.manage.sys.user;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import ml.idream.global.GloablePager;
import ml.idream.manage.sys.department.SysDepartment;
import ml.idream.manage.sys.department.SysDepartmentService;
import ml.idream.manage.sys.role.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysUserService {

    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysDepartmentService sysDepartmentService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public SysUser addUser(String userName,String passWord,String email) throws Exception {
        SysUser user = new SysUser.Builder().setName(userName)
                .setPassWord(passwordEncoder.encode(passWord))
                .setEmail(email).build();
        return save(user);
    }

    public void deleteAll() {
        sysUserDao.deleteAll();
        sysUserDao.deleteAllUserRole();
        sysUserDao.deleteAllUserDepartment();
    }

    @Transactional
    public SysUser save(SysUser user) {
        //保存用户
        sysUserDao.save(user);
        //插入关联关系
        List<Map<String,Object>> userRole = new ArrayList<Map<String,Object>>();
        for(SysRole role : user.getRoles()){
            Map<String,Object> item = new HashMap<String,Object>();
            item.put("userId",user.getId());
            item.put("roleId",role.getId());
            userRole.add(item);
        }
        if(userRole.size() > 0){
            sysUserDao.saveUserRole(userRole);
        }

        //插入部门
        SysDepartment department = user.getSysDepartment();
        if(department != null){
            //用户部门关联
            Map<String,Object> userDepartmentMapper = new HashMap<String,Object>();
            userDepartmentMapper.put("userId",user.getId());
            userDepartmentMapper.put("departmentId",department.getId());
            sysUserDao.saveUserDepartment(userDepartmentMapper);
        }

        return user;
    }

    public SysUser findByNameEquals(String username) {
        return sysUserDao.findByNameEquals(username);
    }

    /*
    * 查询用户
    * */
    public List<SysUser> findUsers(GloablePager gloablePager) {
        Page page = PageHelper.startPage(gloablePager.getStart()).setPageSize(gloablePager.getPageSize()).setPageNum(gloablePager.getPageNum()).setCount(true);
        List<SysUser> ret =  sysUserDao.findAllUsers();
        return ret;
    }
}
