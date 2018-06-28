package ml.idream.manage.sys;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ManageCtl {


    @RequestMapping("/manage")
    public String index(){

        return "/manage/manage-index";
    }
}
