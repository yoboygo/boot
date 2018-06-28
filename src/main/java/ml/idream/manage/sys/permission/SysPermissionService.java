package ml.idream.manage.sys.permission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysPermissionService {

    @Autowired
    private SysPermissionDao sysPermissionDao;

    public void deleteAll() {
        sysPermissionDao.deletAll();
    }

    @Transactional
    public void save(SysPermission permissionManage) {
        sysPermissionDao.save(permissionManage);
    }
}
