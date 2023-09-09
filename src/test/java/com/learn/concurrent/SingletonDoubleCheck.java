package com.learn.concurrent;

/**
 * @author herryhaorui@didiglobal.com
 * @desc
 * @date 2023/4/28 10:46 上午
 */
public class SingletonDoubleCheck {
    private static volatile SingletonDoubleCheck instance;
    private SingletonDoubleCheck() {
    }
    public static SingletonDoubleCheck getInstance() {
        if (instance == null) {
            synchronized (SingletonDoubleCheck.class) {
                if (instance == null) {
                    // 懒汉式
                    // 这个语句其实是3个指令
                    // memory = allocate(); 1.分配对象的内存空间
                    // instance = memory; 3.设置instance指向刚分配的内存地址，注意，此时对象还没有被初始化
                    // ctorInstance; 2.初始化对象
                    // 2，3有可能指令重排，造成另一个线程拿到一个不为空但是未初始化的对象
                    // 加上volatile关键字以后会禁止2，3指令重排
                    instance = new SingletonDoubleCheck();
                }
            }
        }
        return instance;
    }
}
