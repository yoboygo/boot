package ml.idream.security;

import ml.idream.manage.sys.role.SysRole;
import ml.idream.manage.sys.user.SysUser;
import ml.idream.manage.sys.user.SysUserService;
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
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.findByNameEquals(username);
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
