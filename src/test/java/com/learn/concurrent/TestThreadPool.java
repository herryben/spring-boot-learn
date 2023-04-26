package com.learn.concurrent;

import lombok.SneakyThrows;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.*;

public class TestThreadPool {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestThreadPool.class);

    /**
     * 参考 https://www.cnblogs.com/dennyzhangdd/p/7010972.html#_label0_2
     */
    @SneakyThrows
    @Test
    public void testThreadPool() {
//        ExecutorService executorService = Executors.newCachedThreadPool();
//        ExecutorService executorService = Executors.newFixedThreadPool(3);
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
        ExecutorService executorService = new ThreadPoolExecutor(5, 10,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());
        List<Future<String>> futureList = Lists.newArrayList();

        for (int i = 0; i < 20; i++) {
            Future<String> future = executorService.submit(new Task(i));
            futureList.add(future);
        }
        Collections.reverse(futureList);
        while (futureList.size() > 0) {
            Iterator<Future<String>> iterator = futureList.iterator();
            while (iterator.hasNext()) {
                Future<String> future = iterator.next();
                if (future.isDone() && !future.isCancelled()) {
                    LOGGER.info("{}", future.get());
                    iterator.remove();
                } else {
                    Thread.yield();
                }
            }
        }
        executorService.shutdown();
    }

    @Test
    public void testCompletableFuture() throws Exception {
        ExecutorService executorService = new ThreadPoolExecutor(5, 10,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());
        LOGGER.info("future start");
        String res = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s1";
        }, executorService).thenCombine(CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "s2";
        }), (s1, s2) -> {
            String combineResult = String.format("%s + %s", s1, s2);
            LOGGER.info("combineResult={}", combineResult);
            return combineResult;
        }).whenComplete((s, t) -> {
            LOGGER.info("11111111 {}", s);
            // 这里因为没有异常所以会产生NPE
            LOGGER.info("22222222 {}", t.getMessage());
        }).exceptionally(e ->{
            LOGGER.info("3333333 {}", e.getMessage());
            return "hello world";
        }).join();
        LOGGER.info("444444 {}", res);
    }

    @Test
    public void testWhile() throws InterruptedException {
        do {
            System.out.println("haha");
            Thread.sleep(1000);
        }while (false);
    }
}

class Task implements Callable<String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(Task.class);
    private int id;

    public Task(int id) {
        this.id = id;
    }

    @Override
    public String call() throws Exception {
        Thread.sleep(5000 + id * 100);
        LOGGER.info("call() 被调用 {}", Thread.currentThread().getName());
        return "call 方法被调用,任务返回结果：" + id + " " + Thread.currentThread().getName();
    }
}