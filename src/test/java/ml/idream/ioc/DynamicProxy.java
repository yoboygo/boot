package ml.idream.ioc;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class DynamicProxy {

    @Before(value = "getName(*)")
    public void before(){

    }
}
