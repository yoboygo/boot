package ml.idream.manage.sys.user;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import ml.idream.log.DreamLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class SysUserCtl {

    @Autowired
    private SysUserService userService;

    @RequestMapping("/list")
    public String toList(){

        return "/manage/user/user-list";
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
}
