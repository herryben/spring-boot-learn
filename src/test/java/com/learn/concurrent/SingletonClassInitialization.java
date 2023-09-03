package com.learn.concurrent;

/**
 * @author herryhaorui@didiglobal.com
 * @desc
 * @date 2023/4/28 10:58 上午
 */
public class SingletonClassInitialization {
    private SingletonClassInitialization() {
    }

    private static class InstanceHolder {
        // 饿汉式
        // 这个语句其实是3个指令
        // memory = allocate(); 1.分配对象的内存空间
        // instance = memory; 3.设置instance指向刚分配的内存地址，注意，此时对象还没有被初始化
        // ctorInstance; 2.初始化对象
        // 2，3有可能指令重排，造成另一个线程拿到一个不为空但是未初始化的对象
        // 因为读取静态字段InstanceHolder.instance会触发类加载，类加载有对应的锁，保证类只会被一个线程加载一次
        // 这里即使2，3指令重排，也只是对单线程可见，其他线程不可见
        public static SingletonClassInitialization instance = new SingletonClassInitialization();
    }

    public static SingletonClassInitialization getInstance() {
        return InstanceHolder.instance;
    }
}
