package ml.idream.ioc.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class DynamicProxy {

    @Before(value = "execution(* get*(..))")
    public void before(){
        System.out.println("---获取名称之前执行---");
    }
}
