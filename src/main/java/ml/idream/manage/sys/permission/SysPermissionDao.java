package ml.idream.manage.sys.permission;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysPermissionDao{

    SysPermission findPermissionById(@Param("id") Long id);

    List<SysPermission> findPermissionByRoleId(@Param("roleId") Long roleId);

    void deletAll();

    void save(SysPermission sysPermission);
}
