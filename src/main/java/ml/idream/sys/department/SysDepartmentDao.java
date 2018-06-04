package ml.idream.sys.department;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysDepartmentDao extends JpaRepository<SysDepartment,Long> {
}
