package ml.idream.config;

public class DreamClassLoaser extends ClassLoader {

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            // First, check if the class has already been loaded
            Class<?> c = null;
            long t0 = System.nanoTime();
            // If still not found, then invoke findClass in order
            // to find the class.
            try {
                c = findClass(name);
                long t1 = System.nanoTime();
                // this is the defining class loader; record the stats
                sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                sun.misc.PerfCounter.getFindClasses().increment();

            } catch (ClassNotFoundException e) {
                //没有找到类
            }
            if(c == null){
                return super.loadClass(name,resolve);
            }
            if (resolve) {
                resolveClass(c);
            }
            return c;
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return getSystemClassLoader().loadClass(name);
    }
}
