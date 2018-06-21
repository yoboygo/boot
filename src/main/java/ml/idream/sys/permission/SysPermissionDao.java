package ml.idream.sys.permission;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysPermissionDao{

    public SysPermission findPermissionById(@Param("id") Integer id);

    public List<SysPermission> findPermissionByRoleId(@Param("roleId") Integer roleId);
}
