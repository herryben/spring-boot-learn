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
        // 大家一起倒数，数到0执行一个特定的动作
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
                    log.info("thread i={} enter", finalI);
                    // 大家拉起到某一个共同位置，一起往下执行
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                log.info("thread i={} finished NumberWaiting={}", finalI, cyclicBarrier.getNumberWaiting());
            });
            thread.start();
        }

        TimeUnit.SECONDS.sleep(5);
    }
}
