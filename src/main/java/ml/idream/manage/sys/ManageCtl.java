package ml.idream.manage.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.support.SecurityWebApplicationContextUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping(value = "/manage")
public class ManageCtl {

    @RequestMapping(method = RequestMethod.GET)
    public String manage(){
        return "/manage/manage-index";
    }

    /*
    * 管理员欢迎界面
    * */
    @RequestMapping(value = "/welcome",method = RequestMethod.GET)
    public String welcome(HttpServletRequest request, HttpServletResponse response, Model model){
//        response.setHeader("X-Frame-Options", "SAMEORIGIN");
        return "/manage/welcome";
    }

    /*
     * 自定义退出
     * */
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String logOut(HttpServletRequest request,HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        //如果配置了logout-url 则此处地址无效
        return "redirect:/login?error";
    }
}
