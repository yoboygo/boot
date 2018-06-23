package ml.idream.sys.role;

import java.util.List;
import java.util.Map;
/*
* @linke {ml.idream.sys.permission.SysPermissionDao.findByRoleId}
* */
public interface SysRoleDao{

    public List<SysRole> findAll();

    void deleteAll();

    void save(SysRole role);

    void saveRolePermission(List<Map<String,Object>> rolePermission);
}
