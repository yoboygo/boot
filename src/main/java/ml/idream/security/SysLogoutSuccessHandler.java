package ml.idream.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SysLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    public SysLogoutSuccessHandler(String targetUrl) {
        super.setDefaultTargetUrl(targetUrl);
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        super.onLogoutSuccess(request,response,authentication);

        UserDetails user = (UserDetails) authentication.getPrincipal();
        System.out.println("用户 " + user.getUsername() + " 已退出！");
    }

}
