package ml.idream.log;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class LogBeanProcesser implements InstantiationAwareBeanPostProcessor {

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if(!"sysUserCtl".equals(beanName)){
            return true;
        }
        System.out.println("正在处理Bean：" + beanName);
        Annotation[] annotations = bean.getClass().getDeclaredAnnotations();
       Map<String,Object> datas = new HashMap<String,Object>();

        for(Annotation an : annotations){
            System.out.println(an.getClass().getSimpleName());
            if(an instanceof DreamLog){
                datas.put("class",bean.getClass().getName());
            }
            if(an instanceof RequestMapping){
                RequestMapping c = (RequestMapping)an;
                datas.put("value",c.value());
            }
        }
        List<Map<String,Object>> methods = new ArrayList<Map<String,Object>>();
        //反射method
        for(Method method : bean.getClass().getDeclaredMethods()){
            String methodName = method.getName();
            Map<String,Object> item = new HashMap<String,Object>();
            DreamLog dan = (DreamLog) method.getDeclaredAnnotation(DreamLog.class);
            item.put("method",method.getName());
            if(dan != null){
                Map<String,Object> log = new HashMap<String,Object>();
                log.put("name",dan.name());
                log.put("val",dan.value());
                item.put("log",log);
            }
            RequestMapping qan = (RequestMapping)method.getDeclaredAnnotation(RequestMapping.class);
            if(qan != null){
                Map<String,Object> mapping = new HashMap<String,Object>();
                mapping.put("mapping",qan.value());
                mapping.put("name",qan.name());
                RequestMethod[] methodReq = qan.method();
                String ret = "";
                for (RequestMethod rm : methodReq){
                    switch (rm){
                        case GET: ret += "GET ";break;
                        case POST: ret += "POST ";break;
                    }
                }
                mapping.put("method",ret);
                item.put("mapping",mapping);
            }
            methods.add(item);
        }
        datas.put("method",methods);
        if(!datas.isEmpty()){
            System.out.println(JSONObject.wrap(datas).toString());
        }

        return true;
    }
}
