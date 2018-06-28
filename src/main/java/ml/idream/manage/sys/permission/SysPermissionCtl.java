package ml.idream.manage.sys.permission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/*
* 权限控制
* */
@Controller
@RequestMapping("/permission")
public class SysPermissionCtl {

    @Autowired
    private SysPermissionService sysPermissionService;

    @RequestMapping("/list")
    public String toPermission(){
        return "/manage/permission-list";
    }
}
