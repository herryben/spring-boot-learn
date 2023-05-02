package com.learn.concurrent;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 1226. 哲学家进餐
 * https://leetcode.cn/problems/the-dining-philosophers/description/
 * 解题思路：总是让哲学家抢下标的小叉子，这样肯定只会有一个冲突，不会造成死锁
 */
public class DiningPhilosophers {

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
        if (left > right) {
            int tmp = left;
            left = right;
            right = tmp;
        }
        lock[left].lock();
        lock[right].lock();
        pickLeftFork.run();
        pickRightFork.run();
        eat.run();
        putLeftFork.run();
        putRightFork.run();
        lock[left].unlock();
        lock[right].unlock();
    }
}
