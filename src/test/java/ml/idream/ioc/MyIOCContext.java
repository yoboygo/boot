package ml.idream.ioc;

import java.io.File;
import java.io.FileFilter;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

public class MyIOCContext {
    private ClassLoader cl = ClassLoader.getSystemClassLoader();
    private Map<String,Object> beanMap = new HashMap<String,Object>();
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
            Annotation[] annotations = clazz.getAnnotations();
            for(Annotation an : annotations){
                if(an instanceof MyBean){
                    MyBean myBean = (MyBean) an;
                    Object object = clazz.newInstance();
                    String beanName = myBean.value();
                    beanMap.put(beanName,object);
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
