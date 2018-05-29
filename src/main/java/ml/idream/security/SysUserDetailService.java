package ml.idream.security;

import ml.idream.sys.SysRole;
import ml.idream.sys.SysUser;
import ml.idream.sys.user.SysUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysUserDetailService implements UserDetailsService {
    @Autowired
    private SysUserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = userDao.findByNameEquals(username);
        if (sysUser == null) {
            throw new UsernameNotFoundException("Security 验证失败，没用找到用户 " + username);
        }
        List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
        for (SysRole role : sysUser.getRoles()) {
            auths.add(new SimpleGrantedAuthority(role.getName()));

        }

        return new User(username, sysUser.getPassword(), auths);
    }
}
