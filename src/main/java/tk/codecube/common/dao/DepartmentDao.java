package tk.codecube.common.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tk.codecube.common.entity.Department;

@Repository
public interface DepartmentDao extends JpaRepository<Department,Long> {
}
