package ml.idream.sys.user;

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

    @DreamLog(name="方法：",value="toSignIn：跳转到注册页")
    @ApiOperation(value = "跳转到注册页面", notes="没有任何参数")
    @ApiImplicitParam(name = "model",dataTypeClass = Model.class)
    @RequestMapping(value = "/signin",method = RequestMethod.GET)
    public String toSignIn(Model model) throws Exception {
        return "/user/signin";
    }

    @RequestMapping(value = "/signin",method = RequestMethod.POST)
    public String signIn(Model model,String username, String password) throws Exception {
        userService.addUser(username,password);
        System.out.println( "username:" + username + " password:" + password );
        return "redirect:/login";
    }

}
