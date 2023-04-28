package com.learn.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author herryhaorui@didiglobal.com
 * @desc
 * @date 2023/4/28 3:08 下午
 */
@Slf4j
public class FooBar2 {
    private int n;

    public void setN(int n) {
        this.n = n;
    }

    private volatile boolean state = true;

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            while (!state) {
                Thread.yield();
            }
            // printFoo.run() outputs "foo". Do not change or remove this line.
            printFoo.run();
            state = false;
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            while (state) {
                Thread.yield();
            }
            // printBar.run() outputs "bar". Do not change or remove this line.
            printBar.run();
            state = true;
        }
    }

    @Test
    public void testFooBar2() throws InterruptedException {
        int n = 20;
        FooBar2 fooBar = new FooBar2();
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
