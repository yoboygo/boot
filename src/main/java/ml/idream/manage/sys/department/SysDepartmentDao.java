package ml.idream.manage.sys.department;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface SysDepartmentDao {
    void deleteAll();

    void save(SysDepartment department);

    List<SysDepartment> findDepartmentByUserId(String userId);
}
