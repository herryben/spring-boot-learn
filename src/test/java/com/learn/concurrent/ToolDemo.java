package com.learn.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @author herryhaorui@didiglobal.com
 * @desc
 * @date 2023/4/26 5:15 下午
 */
@Slf4j
public class ToolDemo {
    @Test
    public void testCountDownLatch() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(4);
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            Thread thread = new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(finalI);
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("thread i={} finished", finalI);
            });
            thread.start();
        }

        countDownLatch.await();
        log.info("CountDownLatchDemo.testCountDownLatch finished");
    }

    @Test
    public void testCyclicBarrier() throws InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(4);
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            Thread thread = new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(finalI);
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                log.info("thread i={} finished NumberWaiting={}", finalI, cyclicBarrier.getNumberWaiting());
            });
            thread.start();
        }

        TimeUnit.MINUTES.sleep(1);
    }
}
