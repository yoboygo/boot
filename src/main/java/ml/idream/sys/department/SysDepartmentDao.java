package ml.idream.sys.department;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysDepartmentDao {
    void deleteAll();

    void save(SysDepartment department);
}
