package com.learn.concurrent;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author herryhaorui@didiglobal.com
 * @desc https://blog.csdn.net/wat1r/article/details/119130292
 * @date 2023/4/26 7:10 下午
 */
@Slf4j
public class SimpleBlockingQueue {
    private int[] items;
    private ReentrantLock lock;
    private Condition notFull;
    private Condition notEmpty;
    private int count, addIndex, removeIndex;

    public SimpleBlockingQueue() {
    }

    public SimpleBlockingQueue(int capacity) {
        this.items = new int[capacity];
        lock = new ReentrantLock();
        notEmpty = lock.newCondition();
        notFull = lock.newCondition();
    }

    public int getCount() {
        return count;
    }

    public void offer(int item) {
        lock.lock();
        try {
            while (count == items.length)  {
                notFull.await();
            }
            // 先用index
            items[addIndex] = item;
            // 再维护
            if (++addIndex == items.length) {
                addIndex = 0;
            }
            count++;
            notEmpty.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public int poll() {
        lock.lock();
        int item = 0;
        try {
            while (count == 0) {
                notEmpty.await();
            }
            // 先用index
            item = items[removeIndex];
            // 再维护
            if (++removeIndex == items.length) {
                removeIndex = 0;
            }
            count--;
            notFull.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return item;
    }


    public static void main(String[] args) {
        SimpleBlockingQueue blockingQueue = new SimpleBlockingQueue(3);
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 4; i++) {
                int item = blockingQueue.poll();
                log.info("testSimpleBlockingQueue poll item={} count={}", item, blockingQueue.getCount());
            }
        }).start();
        for (int i = 0; i < 4; i++) {
            blockingQueue.offer(i + 1);
            log.info("testSimpleBlockingQueue item={} count={}", i + 1, blockingQueue.getCount());
        }
    }
}
