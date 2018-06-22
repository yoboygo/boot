package ml.idream.sys.user;

import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;

/*
* UserDao
* */
public interface SysUserDao{

    SysUser findByNameEquals(String name);

    void save(SysUser user);

    void saveUserRole(List<Map<String,Object>> userRole);

    void deleteAll();
}