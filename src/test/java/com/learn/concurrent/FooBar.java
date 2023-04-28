package com.learn.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author herryhaorui@didiglobal.com
 * @desc 1115. 交替打印 FooBar
 * https://leetcode.cn/problems/print-foobar-alternately/description/
 * 给你一个类：
 * class FooBar {
 *   public void foo() {
 *     for (int i = 0; i < n; i++) {
 *       print("foo");
 *     }
 *   }
 *   public void bar() {
 *     for (int i = 0; i < n; i++) {
 *       print("bar");
 *     }
 *   }
 * }
 * 两个不同的线程将会共用一个 FooBar 实例：
 * 线程 A 将会调用 foo() 方法，而
 * 线程 B 将会调用 bar() 方法
 * 请设计修改程序，以确保 "foobar" 被输出 n 次。
 * 示例 1：
 * 输入：n = 1
 * 输出："foobar"
 * 解释：这里有两个线程被异步启动。其中一个调用 foo() 方法, 另一个调用 bar() 方法，"foobar" 将被输出一次。
 * 示例 2：
 * 输入：n = 2
 * 输出："foobarfoobar"
 * 解释："foobar" 将被输出两次。
 * @date 2023/4/28 3:08 下午
 */
@Slf4j
public class FooBar {
    private int n;

    public void setN(int n) {
        this.n = n;
    }

    private AtomicBoolean state = new AtomicBoolean(true);

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            while (!state.get()) {
                Thread.yield();
            }
            // printFoo.run() outputs "foo". Do not change or remove this line.
            printFoo.run();
            state.getAndSet(false);
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            while (state.get()) {
                Thread.yield();
            }
            // printBar.run() outputs "bar". Do not change or remove this line.
            printBar.run();
            state.getAndSet(true);
        }
    }

    @Test
    public void testFooBar2() throws InterruptedException {
        int n = 20;
        FooBar fooBar = new FooBar();
        fooBar.setN(n);
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        Runnable fooRun = () -> {
            try {
                fooBar.foo(() -> queue.offer("foo"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable barRun = () -> {
            try {
                fooBar.bar(() -> queue.offer("bar"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        new Thread(barRun).start();
        new Thread(fooRun).start();
        TimeUnit.SECONDS.sleep(1);
        String res = String.join("", queue);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append("foobar");
        }
        log.info("sb={} res{}", sb.toString(), res);
        Assert.assertEquals(true, StringUtils.equals(sb.toString(), res));
    }
}
