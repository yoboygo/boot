package ml.idream.sys.permission;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysPermissionDao{

    @Select("Select * from sys_permission where id = #{id}")
    public SysPermission findById(@Param("id") Integer id);

    @Select("Select p.* from sys_permission p join sys_role_permission m on m.permission_id = p.id where m.role_id = #{roleId}")
    public List<SysPermission> findByRoleId(@Param("roleId") Integer roleId);
}
