package com.learn.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;
import java.util.function.IntConsumer;

/**
 * @author herryhaorui@didiglobal.com
 * @desc 1195. 交替打印字符串
 * https://leetcode.cn/problems/fizz-buzz-multithreaded/
 * 编写一个可以从 1 到 n 输出代表这个数字的字符串的程序，但是：
 * <p>
 * 如果这个数字可以被 3 整除，输出 "fizz"。
 * 如果这个数字可以被 5 整除，输出 "buzz"。
 * 如果这个数字可以同时被 3 和 5 整除，输出 "fizzbuzz"。
 * 例如，当 n = 15，输出： 1, 2, fizz, 4, buzz, fizz, 7, 8, fizz, buzz, 11, fizz, 13, 14, fizzbuzz。
 * <p>
 * 假设有这么一个类：
 * <p>
 * class FizzBuzz {
 * public FizzBuzz(int n) { ... }               // constructor
 * public void fizz(printFizz) { ... }          // only output "fizz"
 * public void buzz(printBuzz) { ... }          // only output "buzz"
 * public void fizzbuzz(printFizzBuzz) { ... }  // only output "fizzbuzz"
 * public void number(printNumber) { ... }      // only output the numbers
 * }
 * 请你实现一个有四个线程的多线程版  FizzBuzz， 同一个 FizzBuzz 实例会被如下四个线程使用：
 * <p>
 * 线程A将调用 fizz() 来判断是否能被 3 整除，如果可以，则输出 fizz。
 * 线程B将调用 buzz() 来判断是否能被 5 整除，如果可以，则输出 buzz。
 * 线程C将调用 fizzbuzz() 来判断是否同时能被 3 和 5 整除，如果可以，则输出 fizzbuzz。
 * 线程D将调用 number() 来实现输出既不能被 3 整除也不能被 5 整除的数字。
 * <p>
 * <p>
 * 提示：
 * <p>
 * 本题已经提供了打印字符串的相关方法，如 printFizz() 等，具体方法名请参考答题模板中的注释部分。
 * @date 2023/4/28 9:19 下午
 */
@Slf4j
public class FizzBuzz2 {
    private final AtomicInteger lock = new AtomicInteger();
    private int n;
    private int state = 1;

    public void setN(int n) {
        this.n = n;
    }

    // printFizz.run() outputs "fizz".
    public void fizz(Runnable printFizz) throws InterruptedException {
        while (state <= n) {
            while (!lock.compareAndSet(0, 1)) {
                LockSupport.parkNanos(1);
            }
            if (state % 3 == 0 && state % 5 != 0 && state <= n) {
                printFizz.run();
                state++;
            }
            lock.set(0);
        }
    }

    // printBuzz.run() outputs "buzz".
    public void buzz(Runnable printBuzz) throws InterruptedException {
        while (state <= n) {
            while (!lock.compareAndSet(0, 1)) {
                LockSupport.parkNanos(1);
            }
            if (state % 5 == 0 && state % 3 != 0 && state <= n) {
                printBuzz.run();
                state++;
            }
            lock.set(0);
        }
    }

    // printFizzBuzz.run() outputs "fizzbuzz".
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        while (state <= n) {
            while (!lock.compareAndSet(0, 1)) {
                LockSupport.parkNanos(1);
            }
            if (state % 5 == 0 && state % 3 == 0 && state <= n) {
                printFizzBuzz.run();
                state++;
            }
            lock.set(0);
        }
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void number(IntConsumer printNumber) throws InterruptedException {
        while (state <= n) {
            while (!lock.compareAndSet(0, 1)) {
                LockSupport.parkNanos(1);
            }
            if (state % 5 != 0 && state % 3 != 0 && state <= n) {
                printNumber.accept(state);
                state++;
            }
            lock.set(0);
        }
    }

    @Test
    public void testFizzBuzz() throws InterruptedException {
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        FizzBuzz2 fizzBuzz = new FizzBuzz2();
        fizzBuzz.setN(15);
        new Thread(() -> {
            try {
                fizzBuzz.fizz(() -> queue.offer("fizz"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                fizzBuzz.buzz(() -> queue.offer("buzz"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                fizzBuzz.fizzbuzz(() -> queue.offer("fizzbuzz"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                fizzBuzz.number(number -> queue.offer(String.valueOf(number)));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        TimeUnit.SECONDS.sleep(1);
        String res = String.join(", ", queue);
        log.info("res={}", res);
        Assert.assertTrue(StringUtils.equals(res, "1, 2, fizz, 4, buzz, fizz, 7, 8, fizz, buzz, 11, fizz, 13, 14, fizzbuzz"));
    }
}
