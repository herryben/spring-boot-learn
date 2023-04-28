package com.learn.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;

/**
 * @author herryhaorui@didiglobal.com
 * @desc
 * @date 2023/4/28 3:42 下午
 */
@Slf4j
public class ZeroEvenOdd1 {
    private int n;

    public void setN(int n) {
        this.n = n;
    }
    private volatile boolean zeroSwitch = true;
    private volatile boolean evenSwitch = false;
    private volatile boolean oddSwitch = false;
    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            while (!zeroSwitch) {
                Thread.yield();
            }
            zeroSwitch = false;
            printNumber.accept(0);
            if (i % 2 == 0) {
                evenSwitch = true;
            } else {
                oddSwitch = true;
            }
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i+=2) {
            while (!evenSwitch) {
                Thread.yield();
            }
            evenSwitch = false;
            printNumber.accept(i);
            zeroSwitch = true;
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i+=2) {
            while (!oddSwitch) {
                Thread.yield();
            }
            oddSwitch = false;
            printNumber.accept(i);
            zeroSwitch = true;
        }
    }

    @Test
    public void testFooBar2() throws InterruptedException {
        int n = 20;
        ZeroEvenOdd1 zeroEvenOdd = new ZeroEvenOdd1();
        zeroEvenOdd.setN(n);
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

        new Thread(() -> {
            try {
                zeroEvenOdd.zero(queue::offer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                zeroEvenOdd.even(queue::offer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                zeroEvenOdd.odd(queue::offer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        TimeUnit.SECONDS.sleep(1);
        String res = queue.stream().map(String::valueOf).collect(Collectors.joining(""));
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            sb.append("0").append(i);
        }
        log.info("sb={} res{}", sb, res);
        Assert.assertEquals(true, StringUtils.equals(sb.toString(), res));
    }
}
