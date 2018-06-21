package ml.idream.sys.user;

import org.apache.ibatis.annotations.*;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;

/*
* UserDao
* */
@Mapper
public interface SysUserDao{

    SysUser findByNameEquals(String name);

    @Options(useGeneratedKeys = true)
    @Insert("")
    void save(SysUser user);

    @Options(useGeneratedKeys = true)
    void saveUserRole(List<Map<String,Object>> userRole);

    @Delete("delete from sys_user")
    void deleteAll();
}