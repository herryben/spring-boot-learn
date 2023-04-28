package com.learn.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author herryhaorui@didiglobal.com
 * @desc 1114. 按序打印
 * https://leetcode.cn/problems/print-in-order/description/
 * 给你一个类：
 * public class Foo {
 *   public void first() { print("first"); }
 *   public void second() { print("second"); }
 *   public void third() { print("third"); }
 * }
 * 三个不同的线程 A、B、C 将会共用一个 Foo 实例。
 * 个不同的线程 A、B、C 将会共用一个 Foo 实例。
 * 线程 A 将会调用 first() 方法
 * 线程 B 将会调用 second() 方法
 * 线程 C 将会调用 third() 方法
 * 请设计修改程序，以确保 second() 方法在 first() 方法之后被执行，third() 方法在 second() 方法之后被执行。
 * 提示：
 * 尽管输入中的数字似乎暗示了顺序，但是我们并不保证线程在操作系统中的调度顺序。
 * 你看到的输入格式主要是为了确保测试的全面性。
 * 示例 1：
 * 输入：nums = [1,2,3]
 * 输出："firstsecondthird"
 * 解释：
 *  有三个线程会被异步启动。输入 [1,2,3] 表示线程 A 将会调用 first() 方法，线程 B 将会调用 second() 方法，线程 C 将会调用 third() 方法。正确的输出是 "firstsecondthird"。
 * 示例 2：
 * 输入：nums = [1,3,2]
 * 输出："firstsecondthird"
 * 解释：
 *  输入 [1,3,2] 表示线程 A 将会调用 first() 方法，线程 B 将会调用 third() 方法，线程 C 将会调用 second() 方法。正确的输出是 "firstsecondthird"。
 * @date 2023/4/28 12:05 下午
 */
@Slf4j
public class PrintInOrder {
    Semaphore secondSemaphore = new Semaphore(0);
    Semaphore thirdSemaphore = new Semaphore(0);
    public void first(Runnable printFirst) throws InterruptedException {
        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        secondSemaphore.release();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        secondSemaphore.acquire();
        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
        thirdSemaphore.release();
    }

    public void third(Runnable printThird) throws InterruptedException {
        thirdSemaphore.acquire();
        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
    }

    @Test
    public void testPrintInOrder1() throws InterruptedException {
        int[] nums = new int[]{1,2,3};
        Queue<String> queue = generateTestCase(nums);
        TimeUnit.SECONDS.sleep(2);
        String res = String.join("", queue);
        log.info("res={}", res);
        Assert.assertEquals(true, StringUtils.equals("firstsecondthird", res));
    }

    @Test
    public void testPrintInOrder2() throws InterruptedException {
        int[] nums = new int[]{1,3,2};
        Queue<String> queue = generateTestCase(nums);
        TimeUnit.SECONDS.sleep(2);
        String res = String.join("", queue);
        log.info("res={}", res);
        Assert.assertEquals(true, StringUtils.equals("firstsecondthird", res));
    }

    @Test
    public void testPrintInOrder3() throws InterruptedException {
        int[] nums = new int[]{3,1,2};
        Queue<String> queue = generateTestCase(nums);
        TimeUnit.SECONDS.sleep(2);
        String res = String.join("", queue);
        log.info("res={}", res);
        Assert.assertEquals(true, StringUtils.equals("firstsecondthird", res));
    }

    @Test
    public void testPrintInOrder4() throws InterruptedException {
        int[] nums = new int[]{3,2,1};
        Queue<String> queue = generateTestCase(nums);
        TimeUnit.SECONDS.sleep(2);
        String res = String.join("", queue);
        log.info("res={}", res);
        Assert.assertEquals(true, StringUtils.equals("firstsecondthird", res));
    }

    @Test
    public void testPrintInOrder5() throws InterruptedException {
        int[] nums = new int[]{2,1,3};
        Queue<String> queue = generateTestCase(nums);
        TimeUnit.SECONDS.sleep(2);
        String res = String.join("", queue);
        log.info("res={}", res);
        Assert.assertEquals(true, StringUtils.equals("firstsecondthird", res));
    }

    @Test
    public void testPrintInOrder6() throws InterruptedException {
        int[] nums = new int[]{2,3,1};
        Queue<String> queue = generateTestCase(nums);
        TimeUnit.SECONDS.sleep(2);
        String res = String.join("", queue);
        log.info("res={}", res);
        Assert.assertEquals(true, StringUtils.equals("firstsecondthird", res));
    }

    public BlockingQueue<String> generateTestCase(int[] nums) {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(16);
        PrintInOrder printInOrder = new PrintInOrder();
        Runnable first = () -> {
            try {
                printInOrder.first(() -> queue.offer("first"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Runnable second = () -> {
            try {
                printInOrder.second(() -> queue.offer("second"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Runnable third = () -> {
            try {
                printInOrder.third(() -> queue.offer("third"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Runnable[] runnableArray = new Runnable[]{first, second, third};
        for (int num: nums) {
            new Thread(runnableArray[num - 1]).start();
        }
        return queue;
    }
}
