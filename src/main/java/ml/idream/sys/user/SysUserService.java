package ml.idream.sys.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SysUserService {

    @Autowired
    private SysUserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public SysUser addUser(String userName,String passWord) throws Exception {
        SysUser user = new SysUser.Builder().setName(userName).setPassWord(passwordEncoder.encode(passWord)).build();
        userDao.save(user);
        return user;
    }
}
