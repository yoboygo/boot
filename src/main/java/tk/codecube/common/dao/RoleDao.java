package tk.codecube.common.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tk.codecube.common.entity.SysRole;
@Repository
public interface RoleDao extends JpaRepository<SysRole,Long> {
}
