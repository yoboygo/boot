package ml.idream.sys.department;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface SysDepartmentDao {
    @Delete("delete from sys_department")
    void deleteAll();

    @Options(useGeneratedKeys = true)
    @Insert("insert into sys_department (name) values (#{name})")
    void save(SysDepartment department);
}
