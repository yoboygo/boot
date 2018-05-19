package tk.codecube.common.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tk.codecube.common.entity.Role;

@Repository
public interface RoleDao extends JpaRepository<Role,Long> {
}
