package ml.idream.manage.sys.department;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysDepartmentService {

    @Autowired
    private SysDepartmentDao sysDepartmentDao;

    public void deleteAll() {
        sysDepartmentDao.deleteAll();
    }

    public void save(SysDepartment sysDepartment) {
        sysDepartmentDao.save(sysDepartment);
    }
}
