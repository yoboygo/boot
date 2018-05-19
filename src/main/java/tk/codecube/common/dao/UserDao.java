package tk.codecube.common.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tk.codecube.common.entity.User;

import java.util.Date;
import java.util.List;

/*
* UserDao
* */
@Repository
public interface UserDao extends JpaRepository<User,Long> {
    User findByNameLikeOrderByIdAsc(String name);
    List<User> getByCreatedateLessThan(Date date);

}
