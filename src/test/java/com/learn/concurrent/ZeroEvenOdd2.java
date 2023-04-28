package com.learn.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;

/**
 * @author herryhaorui@didiglobal.com
 * @desc
 * @date 2023/4/28 3:42 下午
 */
@Slf4j
public class ZeroEvenOdd2 {
    private int n;

    public void setN(int n) {
        this.n = n;
    }
    private ReentrantLock lock = new ReentrantLock();
    private int state = 0;
    private Condition zeroCondition = lock.newCondition();
    private Condition oddCondition = lock.newCondition();
    private Condition evenCondition = lock.newCondition();
    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            lock.lock();
            while (state != 0) {
                zeroCondition.await();
            }
            printNumber.accept(0);
            if (i % 2 == 0) {
                state = 2;
                evenCondition.signal();
            } else {
                state = 1;
                oddCondition.signal();
            }
            lock.unlock();
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i+=2) {
            lock.lock();
            while (state != 2) {
                evenCondition.await();
            }
            printNumber.accept(i);
            zeroCondition.signal();
            state = 0;
            lock.unlock();
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i+=2) {
            lock.lock();
            while (state != 1) {
                oddCondition.await();
            }
            printNumber.accept(i);
            zeroCondition.signal();
            state = 0;
            lock.unlock();
        }
    }

    @Test
    public void testFooBar2() throws InterruptedException {
        int n = 100;
        ZeroEvenOdd2 zeroEvenOdd = new ZeroEvenOdd2();
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
