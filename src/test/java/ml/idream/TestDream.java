package ml.idream;

import ml.idream.config.DreamClassLoaser;
import ml.idream.manage.sys.user.SysUser;
import org.junit.Test;
import org.springframework.util.AntPathMatcher;

public class TestDream {

    @Test
    public void testClassLoaser() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        String path = "ml.idream.manage.sys.user.SysUser";
        DreamClassLoaser dLoader = new DreamClassLoaser();
        Class<?> clazz = dLoader.loadClass(path);
        SysUser sysUser = (SysUser) clazz.newInstance();
        sysUser.setName("abc");
        sysUser.setPassword("qer");
        System.out.println(sysUser.getName() + "-->" + sysUser.getPassword());
        Class<?> clazz2 = dLoader.loadClass(path);
        System.out.println(clazz.equals(clazz2));

    }

    @Test
    public void testAntMatcher(){
        AntPathMatcher managermatcher = new AntPathMatcher();
        String path = "/css/manage/index.css";
        System.out.println(managermatcher.match("/manage/**",path));
    }
}
