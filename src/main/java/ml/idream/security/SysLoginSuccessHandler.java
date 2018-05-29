package ml.idream.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SysLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    public SysLoginSuccessHandler(String forwardUrl) {
        super(forwardUrl);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        super.onAuthenticationSuccess(request,response,authentication);
        UserDetails user = (UserDetails)authentication.getPrincipal();
        System.out.println("用户 " + user.getUsername() + " 登陆成功！");
    }
}
