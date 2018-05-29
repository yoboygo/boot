package ml.idream.sys.user;

import ml.idream.sys.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserService {

    @Autowired
    private SysUserDao userDao;

    public SysUser addUser(String userName,String passWord) throws Exception {
        SysUser user = new SysUser.Builder().setName(userName).setPassWord(passWord).build();
        userDao.save(user);
        return user;
    }
}
