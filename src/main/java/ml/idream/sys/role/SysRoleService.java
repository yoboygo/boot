package ml.idream.sys.role;

import ml.idream.sys.permission.SysPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleService {

    @Autowired
    private SysRoleDao sysRoleDao;

    public void save(SysRole sysRole) {
        //保存角色信息
        sysRoleDao.save(sysRole);
        //保存角色关联关系
        List<Map<String,Object>> rolePermission = new ArrayList<Map<String,Object>>();
        for(SysPermission permission : sysRole.getPermissions()){
            Map<String,Object> item = new HashMap<String,Object>();
            item.put("roleId",sysRole.getId());
            item.put("permissionId",permission.getId());
            rolePermission.add(item);
        }
        sysRoleDao.saveRolePermission(rolePermission);
    }

    public void deleteAll() {
        sysRoleDao.deleteAll();
    }
}
