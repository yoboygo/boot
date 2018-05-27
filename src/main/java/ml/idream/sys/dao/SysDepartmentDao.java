package ml.idream.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ml.idream.sys.SysDepartment;

@Repository
public interface SysDepartmentDao extends JpaRepository<SysDepartment,Long> {
}
