package ml.idream.sys.role;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;
/*
* @linke {ml.idream.sys.permission.SysPermissionDao.findByRoleId}
* */
@Mapper
public interface SysRoleDao{

    @Select("select * from sys_role")
    @Results({
            @Result(property = "permissions",column = "id",many = @Many(select = "ml.idream.sys.permission.SysPermissionDao.findByRoleId"))
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
}
