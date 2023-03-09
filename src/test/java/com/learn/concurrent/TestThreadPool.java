package com.learn.concurrent;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.*;

public class TestThreadPool {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestThreadPool.class);
    @Test
    public void testThreadPool() {
//        ExecutorService executorService = Executors.newCachedThreadPool();
//        ExecutorService executorService = Executors.newFixedThreadPool(3);
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
        ExecutorService executorService = new ThreadPoolExecutor(5, 10,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), new ThreadPoolExecutor.CallerRunsPolicy());
        List<Future<String>> featureList = Lists.newArrayList();

        for (int i = 0; i < 20; i++) {
            Future<String> future = executorService.submit(new Task(i));
            featureList.add(future);
        }

        for (final Future<String> future: featureList) {
            try {
                LOGGER.info("{}", future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();
    }

    @Test
    public void testCompletableFuture() throws Exception {
        String res = CompletableFuture.supplyAsync(() -> {
            return "s1";
        }).whenComplete((s, t) -> {
            LOGGER.info("11111111 {}", s);
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
        Thread.sleep(5000);
        LOGGER.info("call() 被调用 {}", Thread.currentThread().getName());
        return "call 方法被调用,任务返回结果：" + id + " " + Thread.currentThread().getName();
    }
}