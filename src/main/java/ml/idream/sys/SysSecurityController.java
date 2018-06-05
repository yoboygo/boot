package ml.idream.sys;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import ml.idream.log.DreamLog;
import ml.idream.sys.user.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SysSecurityController {

    @Autowired
    private SysUserService userService;

    @DreamLog(name="方法：",value="toSignIn：跳转到注册页")
    @ApiOperation(value = "跳转到注册页面", notes="没有任何参数")
    @ApiImplicitParam(name = "model",dataTypeClass = Model.class)
    @RequestMapping(value = "/signup",method = RequestMethod.GET)
    public String toSignUp(Model model) throws Exception {
        return "/signup";
    }

    /*
     * 注册新用户
     * */
    @RequestMapping(value = "/signup",method = RequestMethod.POST)
    public String signUp(Model model,String username, String password) throws Exception {
        userService.addUser(username,password);
        System.out.println( "username:" + username + " password:" + password );
        return "redirect:/login";
    }
}
