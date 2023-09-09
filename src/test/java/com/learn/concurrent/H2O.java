package com.learn.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author herryhaorui@didiglobal.com
 * @desc 1117. H2O 生成
 * https://leetcode.cn/problems/building-h2o/
 * 现在有两种线程，氧 oxygen 和氢 hydrogen，你的目标是组织这两种线程来产生水分子。
 *
 * 存在一个屏障（barrier）使得每个线程必须等候直到一个完整水分子能够被产生出来。
 *
 * 氢和氧线程会被分别给予 releaseHydrogen 和 releaseOxygen 方法来允许它们突破屏障。
 *
 * 这些线程应该三三成组突破屏障并能立即组合产生一个水分子。
 *
 * 你必须保证产生一个水分子所需线程的结合必须发生在下一个水分子产生之前。
 *
 * 换句话说:
 *
 * 如果一个氧线程到达屏障时没有氢线程到达，它必须等候直到两个氢线程到达。
 * 如果一个氢线程到达屏障时没有其它线程到达，它必须等候直到一个氧线程和另一个氢线程到达。
 * 书写满足这些限制条件的氢、氧线程同步代码。
 * 示例 1:
 *
 * 输入: water = "HOH"
 * 输出: "HHO"
 * 解释: "HOH" 和 "OHH" 依然都是有效解。
 * 示例 2:
 *
 * 输入: water = "OOHHHH"
 * 输出: "HHOHHO"
 * 解释: "HOHHHO", "OHHHHO", "HHOHOH", "HOHHOH", "OHHHOH", "HHOOHH", "HOHOHH" 和 "OHHOHH" 依然都是有效解。
 * @date 2023/4/28 5:36 下午
 */
@Slf4j
public class H2O {
    // 初始化就有一个氧
    Semaphore oxygenSemaphore = new Semaphore(2);
    Semaphore hydrogenSemaphore = new Semaphore(0);
    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        // 一次需要0.5个氧
        oxygenSemaphore.acquire();
        // releaseHydrogen.run() outputs "H". Do not change or remove this line.
        releaseHydrogen.run();
        // 产生一个氢
        hydrogenSemaphore.release();
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        // 一次需要2个氢
        hydrogenSemaphore.acquire(2);
        // releaseOxygen.run() outputs "O". Do not change or remove this line.
        releaseOxygen.run();
        // 产生一个氧
        oxygenSemaphore.release(2);
    }

    @Test
    public void testH2O() throws InterruptedException {
        H2O h2O = new H2O();
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        Runnable hydrogenRun = () -> {
            try {
                h2O.hydrogen(() -> {
                    queue.offer("H");
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Runnable oxygenRun = () -> {
            try {
                h2O.oxygen(() -> {
                    queue.offer("O");
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Map<Character, Runnable> map = new HashMap<>();
        map.put('H', hydrogenRun);
        map.put('O', oxygenRun);

        String water = "OOHHHH";
        for (char ch: water.toCharArray()) {
            new Thread(map.get(ch)).start();
        }
        TimeUnit.SECONDS.sleep(5);
        log.info("res={}", String.join("", queue));
    }
}
