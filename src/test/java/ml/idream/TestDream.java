package ml.idream;

import ml.idream.config.DreamClassLoaser;
import ml.idream.sys.user.SysUser;
import org.junit.Test;

public class TestDream {

    @Test
    public void testClassLoaser() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        String path = "ml.idream.sys.user.SysUser";
        DreamClassLoaser dLoader = new DreamClassLoaser();
        Class<?> clazz = dLoader.loadClass(path);
        SysUser sysUser = (SysUser) clazz.newInstance();
        sysUser.setName("abc");
        sysUser.setPassword("qer");
        System.out.println(sysUser.getName() + "-->" + sysUser.getPassword());
        Class<?> clazz2 = dLoader.loadClass(path);
        System.out.println(clazz.equals(clazz2));

    }
}
