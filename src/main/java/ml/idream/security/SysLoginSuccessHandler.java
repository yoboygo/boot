package ml.idream.security;

import ml.idream.manage.sys.permission.SysPermission;
import ml.idream.manage.sys.role.SysRole;
import ml.idream.manage.sys.user.SysUser;
import ml.idream.manage.sys.user.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SysLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private SysUserService userService;

    private AntPathMatcher adminMatcher = new AntPathMatcher();

    public SysLoginSuccessHandler() {

    }

    public SysLoginSuccessHandler(String forwardUrl) {
        super(forwardUrl);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails user = (UserDetails)authentication.getPrincipal();
        System.out.println("用户 " + user.getUsername() + " 登陆成功！");
        String successUrl = "/home?login";
        SysUser sysUser = userService.findByNameEquals(user.getUsername());
        for(SysRole role : sysUser.getRoles()){
           for(SysPermission permission : role.getPermissions()){
                if(adminMatcher.match("/manage/**",permission.getUrlPattern())){
                    successUrl = "/admin";
                }
           }
        }
        super.setDefaultTargetUrl(successUrl);
        super.onAuthenticationSuccess(request,response,authentication);
    }
}
