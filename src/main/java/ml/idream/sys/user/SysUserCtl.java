package ml.idream.sys.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SysUserCtl {

    @Autowired
    private SysUserService userService;

    @RequestMapping(value = "/signin",method = RequestMethod.GET)
    public String toSignIn(Model model) throws Exception {
        return "/user/signin";
    }
    @RequestMapping(value = "/signin",method = RequestMethod.POST)
    public String signIn(Model model,String username,String password) throws Exception {
        userService.addUser(username,password);
        System.out.println( "username:" + username + " password:" + password );
        return "redirect:/home";
    }
}
