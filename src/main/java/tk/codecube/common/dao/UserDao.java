package tk.codecube.common.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tk.codecube.common.entity.SysUser;

import java.util.Date;
import java.util.List;

/*
* UserDao
* */
@Repository
public interface UserDao extends JpaRepository<SysUser,Long> {
    SysUser findByNameLikeOrderByIdAsc(String name);
    List<SysUser> getByCreatedateLessThan(Date date);
    SysUser findByNameEquals(String name);

}
