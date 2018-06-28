package ml.idream.manage.sys.department;

import java.util.List;

public interface SysDepartmentDao {
    void deleteAll();

    void save(SysDepartment department);

    List<SysDepartment> findDepartmentByUserId(String userId);
}
