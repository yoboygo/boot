package ml.idream.ioc;

import ml.idream.ioc.aop.BeforeDynamicProxy;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.io.File;
import java.io.FileFilter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MyIOCContext {
    private ClassLoader cl = ClassLoader.getSystemClassLoader();
    private Map<String,Object> beanMap = new HashMap<String,Object>();
    private Map<String,Object> aspectBeanMap = new HashMap<String,Object>();
    private String basePackage;

    public MyIOCContext(){

    }
    public MyIOCContext(String basePackage) {
        this.basePackage = basePackage;
    }

    public Object getBean(String beanName){
        File baseFile = new File(convertPackageToPath(this.basePackage));
        reflashContext(baseFile);
        return beanMap.get(beanName);
    }

    /*
    * 刷新上下文
    * */
    private void reflashContext(File baseFile) {
        if(baseFile.isDirectory()){
            File[] files = baseFile.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return  pathname.getName().indexOf(".class") != -1 || pathname.isDirectory();
                }
            });
            for(File file : files){
                reflashContext(file);
            }
        }else{
            readBean(baseFile);
        }
    }

    /*
    * 读取Bean
    * */
    private void readBean(File baseFile) {
        try {
            String basePath = cl.getResource("").getPath()
                .replaceFirst("/","").replace("/",File.separator);
            String beanPackage = baseFile.getAbsolutePath().replace(basePath,"")
                    .replace(File.separator,".").replace(".class","");
            Class<?> clazz = cl.loadClass(beanPackage);
            MyBean myBean = clazz.getAnnotation(MyBean.class);
            if(myBean != null){

                Object object = clazz.newInstance();
                String beanName = myBean.value();
                beanMap.put(beanName,object);
            }

            Aspect aspect = clazz.getAnnotation(Aspect.class);
            if(aspect != null){
                for(Method method : clazz.getDeclaredMethods()){
                    Before before = method.getDeclaredAnnotation(Before.class);
                    if(before != null){

                    }
                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }

    private String convertPackageToPath(String basePackage){

        String basePath = cl.getResource("").getPath();
        if(basePackage != null && basePackage != ""){
            basePath += basePackage.replace(".",File.separator);
        }
        return basePath;
    }
}
