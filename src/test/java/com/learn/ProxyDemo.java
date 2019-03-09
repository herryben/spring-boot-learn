package com.learn;

import org.junit.Test;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyDemo {
    @Test
    public void testJDKProxy(){
        User user = () -> System.out.println("This is user.save");
        User proxy = (User) Proxy.newProxyInstance(user.getClass().getClassLoader(), user.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object result = method.invoke(user, args);
                System.out.println("This is proxy result = " + result);
                return result;
            }
        });
        proxy.save();
    }

    @Test
    public void testCgLibProxy(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Man.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("This is proxy method");
                Object o1 = methodProxy.invokeSuper(o, objects);
                return o1;
            }
        });
        Man man = (Man) enhancer.create();
        man.speak();
    }
}
interface User {
    void save();
}
class Man {
    public void speak(){
        System.out.println("Man speak");
    }
}