package ml.idream.manage.sys.user;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/*
* UserDao
* */
@Mapper
public interface SysUserDao{

    SysUser findByNameEquals(String name);

    void save(SysUser user);

    void saveUserRole(List<Map<String,Object>> userRole);

    void deleteAll();

    void saveUserDepartment(Map<String,Object> userDepartmentMapper);

    void deleteAllUserRole();

    void deleteAllUserDepartment();

    List<SysUser> findAllUsers();
}