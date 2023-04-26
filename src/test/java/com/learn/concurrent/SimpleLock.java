package com.learn.concurrent;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author herryhaorui@didiglobal.com
 * @desc
 * @date 2023/4/26 8:02 下午
 */
@Slf4j
public class SimpleLock {
    private Sync sync = new Sync();
    public void lock() {
        sync.lock();
    }

    public boolean unlock() {
        return sync.unlock();
    }

    static final class Sync extends AbstractQueuedSynchronizer {
        @Override
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0, 1)) {
//                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            if (getState() == 0) {
                throw new IllegalMonitorStateException();
            }
//            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        public void lock() {
            acquire(1);
        }

        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        public boolean unlock() {
            return release(1);
        }
    }

    @Test
    public void testSimpleLock() throws InterruptedException {
        SimpleLock lock = new SimpleLock();
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() -> {
                lock.lock();
                try {
                    TimeUnit.SECONDS.sleep(finalI);
                    log.info("testSimpleLock finalI={}", finalI);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }).start();
        }
        TimeUnit.SECONDS.sleep(12);
    }
}
