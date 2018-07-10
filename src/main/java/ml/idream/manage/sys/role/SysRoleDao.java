package ml.idream.manage.sys.role;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
/*
* @linke {ml.idream.manage.sys.permission.SysPermissionDao.findByRoleId}
* */
@Mapper
public interface SysRoleDao{

    public List<SysRole> findAll();

    void deleteAll();

    void save(SysRole role);

    List<SysRole> findRoleByUserId(Long userId);

    void saveRolePermission(List<Map<String,Object>> rolePermission);

    void deleteAllRolePermission();
}
