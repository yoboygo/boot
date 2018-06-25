package ml.idream.sys.user;

import ml.idream.sys.role.SysRole;
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
    private PasswordEncoder passwordEncoder;

    public SysUser addUser(String userName,String passWord,String email) throws Exception {
        SysUser user = new SysUser.Builder().setName(userName)
                .setPassWord(passwordEncoder.encode(passWord))
                .setEmail(email).build();
        return save(user);
    }

    public void deleteAll() {
        sysUserDao.deleteAll();
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
        return user;
    }

    public SysUser findByNameEquals(String username) {
        return sysUserDao.findByNameEquals(username);
    }
}
