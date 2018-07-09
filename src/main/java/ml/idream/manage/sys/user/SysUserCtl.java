package ml.idream.manage.sys.user;

import ml.idream.global.GloablePager;
import ml.idream.global.ResBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class SysUserCtl {

    @Autowired
    private SysUserService userService;

    /*
     * 跳转到注册页面
     * */
    @RequestMapping(value = "/signup",method = RequestMethod.GET)
    public String toSignUp(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        System.out.println( "username:" + username + " password:" + password );
        return "/signup";
    }
    /*
     * 注册新用户
     * */
    @RequestMapping(value = "/signup",method = RequestMethod.POST)
    public String signUp(Model model,String username, String password,String email) throws Exception {
        userService.addUser(username,password,email);
        System.out.println( "username:" + username + " password:" + password );
        return "redirect:/login";
    }


    @RequestMapping(value = "/user/list",method = RequestMethod.GET)
    public String toList(){

        return "/manage/user/user-list";
    }

    /*
    * 查询用户列表
    * */
    @RequestMapping(value = "/user/list", method = RequestMethod.POST)
    public ResBean list(GloablePager gloablePager){
        ResBean ret = new ResBean();
        List<SysUser> users = userService.findUsers(gloablePager);
        ret.setDatas(users);
        return ret;
    }
}
