package ml.idream.sys.role;

import ml.idream.sys.permission.SysPermission;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface SysRoleDao{

    @Select("select * from sys_role")
    @Results({
            @Result(property = "permissions",column = "id", javaType = List.class,many = @Many(select = "ml.idream.sys.permission.SysPermissionDao.findByRoleId"))
//            @Result(property = "permissions",column = "id", javaType = List.class,many = @Many(select = "findPermissionByRoleId"))
    })
    public List<SysRole> findAll();

    @Delete("delete from sys_role")
    void deleteAll();

    @Options(useGeneratedKeys = true)
    @Insert("insert into sys_role (name) values (#{name})")
    void save(SysRole role);

    @Options(useGeneratedKeys = true)
    @Insert("insert into sys_role_permissioin (role_id,permission_id) values (#{roleId},#{permissionId})")
    void saveRolePermission(List<Map<String,Object>> rolePermission);

    @Select("Select p.* from sys_permission p join sys_role_permission m on m.permission_id = p.id where m.role_id = #{roleId}")
    public List<SysPermission> findPermissionByRoleId(@Param("roleId") Long roleId);
}
