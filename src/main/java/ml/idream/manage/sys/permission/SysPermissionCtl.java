package ml.idream.manage.sys.permission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*
* 权限控制
* */
@Controller
@RequestMapping(value = "/permission")
public class SysPermissionCtl {

    @Autowired
    private SysPermissionService sysPermissionService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String toPermission(){
        return "/manage/permission-list";
    }
}
