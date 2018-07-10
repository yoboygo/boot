package ml.idream.manage.sys;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/admin")
public class ManageCtl {


    @RequestMapping(method = RequestMethod.GET)
    public String manage(){

        return "/manage/manage-index";
    }
}
