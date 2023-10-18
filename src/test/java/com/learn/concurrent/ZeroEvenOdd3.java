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
 * @date 2023/10/18 2:51 下午
 */
@Slf4j
public class ZeroEvenOdd3 {

    private int n;

    public void setN(int n) {
        this.n = n;
    }

    private volatile int state = 0;
    private Semaphore zeroSam = new Semaphore(1);
    private Semaphore evensam = new  Semaphore(0);
    private Semaphore oddSam = new  Semaphore(0);
    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        while (state <= n) {
            zeroSam.acquire(1);
            if (state < n) {
                printNumber.accept(0);
                if (state % 2 == 0) {
                    evensam.release(1);
                } else {
                    oddSam.release(1);
                }
            }
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        while (state <= n) {
            evensam.acquire(1);
            state++;
            printNumber.accept(state);
            zeroSam.release(1);
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        while (state <= n) {
            oddSam.acquire(1);
            state++;
            printNumber.accept(state);
            zeroSam.release(1);
        }
    }

    @Test
    public void testFooBar3() throws InterruptedException {
        int n = 20;
        ZeroEvenOdd3 zeroEvenOdd = new ZeroEvenOdd3();
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
