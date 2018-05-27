package ml.idream.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ml.idream.sys.SysRole;
@Repository
public interface SysRoleDao extends JpaRepository<SysRole,Long> {
}
