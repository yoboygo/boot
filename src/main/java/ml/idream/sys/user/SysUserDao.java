package ml.idream.sys.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ml.idream.sys.SysUser;

import java.util.Date;
import java.util.List;

/*
* UserDao
* */
@Repository
public interface SysUserDao extends JpaRepository<SysUser,Long> {
    SysUser findByNameLikeOrderByIdAsc(String name);
    List<SysUser> getByCreatedateLessThan(Date date);
    SysUser findByNameEquals(String name);

}
