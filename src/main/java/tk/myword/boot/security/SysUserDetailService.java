package tk.myword.boot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import tk.codecube.common.dao.UserDao;
import tk.codecube.common.entity.SysRole;
import tk.codecube.common.entity.SysUser;

import java.util.ArrayList;
import java.util.List;

public class SysUserDetailService implements UserDetailsService {
    @Autowired
    private UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = userDao.findByNameEquals(username);
        if(sysUser == null){
            return null;
        }
        List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
        for(SysRole role : sysUser.getRoles()){
            auths.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new User(username,sysUser.getPassword(),auths);
    }
}
