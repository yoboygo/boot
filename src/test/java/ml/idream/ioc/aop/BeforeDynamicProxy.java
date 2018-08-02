package ml.idream.ioc.aop;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class BeforeDynamicProxy implements MethodInterceptor {

    private Method methodBefore;
    private Object[] args;
    private Object methodObject;

    public BeforeDynamicProxy() {

    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        Object ret = methodProxy.invoke(o,objects);

        return ret;
    }
}
