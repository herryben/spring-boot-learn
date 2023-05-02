package com.learn.concurrent;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 1226. 哲学家进餐
 * https://leetcode.cn/problems/the-dining-philosophers/description/
 * 解题思路：总是一个哲学家获取一把锁才进餐，否则放弃获取到的锁
 */
public class DiningPhilosophers2 {

    ReentrantLock[] lock = new ReentrantLock[]{
            new ReentrantLock(),
            new ReentrantLock(),
            new ReentrantLock(),
            new ReentrantLock(),
            new ReentrantLock(),
    };

    // call the run() method of any runnable to execute its code
    public void wantsToEat(int philosopher,
                           Runnable pickLeftFork,
                           Runnable pickRightFork,
                           Runnable eat,
                           Runnable putLeftFork,
                           Runnable putRightFork) throws InterruptedException {
        int left = philosopher;
        int right = (philosopher + 1) % 5;
        while (true) {
            while (!lock[left].tryLock()) {
                Thread.yield();
            }
            if (lock[right].tryLock()) {
                break;
            } else {
                lock[left].unlock();
            }
            // 不加这个会超时
            Thread.yield();
        }
        pickLeftFork.run();
        pickRightFork.run();
        eat.run();
        putLeftFork.run();
        putRightFork.run();
        lock[left].unlock();
        lock[right].unlock();
    }
}
