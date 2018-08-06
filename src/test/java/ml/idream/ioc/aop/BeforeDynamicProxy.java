package ml.idream.ioc.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class BeforeDynamicProxy implements MethodInterceptor {

    private Method methodBefore;
    private Object[] args;
    private Object methodObject;

    public BeforeDynamicProxy( Object methodObject,Method methodBefore,Object[] args) {

        this.methodObject = methodObject;
        this.methodBefore = methodBefore;
        this.args = args;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        if(method.equals(methodBefore)){
            methodBefore.invoke(methodObject,objects);
        }
        Object ret = methodProxy.invoke(o,objects);

        return ret;
    }

    public Object getProxy(Class clazz){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }
}
