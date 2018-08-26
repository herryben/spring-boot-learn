package com.learn;

import com.learn.command.GetUserServiceCommand;
import com.learn.command.HelloWorldCommand;
import com.learn.command.HystrixThreadPoolCommand;
import com.learn.service.UserService;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestHystrix {
    @Autowired
    private UserService userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(TestHystrix.class);
    @Test
    public void testGetUserServiceCommend() {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        GetUserServiceCommand getUserServiceCommend1 = new GetUserServiceCommand(userService, 1);
        GetUserServiceCommand getUserServiceCommend2 = new GetUserServiceCommand(userService, 1);
        try {
            getUserServiceCommend1.execute();
            getUserServiceCommend2.execute();
            Assert.assertFalse(getUserServiceCommend1.isResponseFromCache());
            Assert.assertTrue(getUserServiceCommend2.isResponseFromCache());
        } finally {
            context.shutdown();
        }
    }

    @Test
    public void testBatchRequest() throws ExecutionException, InterruptedException {
        List<CompletableFuture<String>> futures = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            futures.add(CompletableFuture.supplyAsync(() -> "" + finalI));
        }
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).get();
    }

    @Test
    public void testHelloWorld() throws Exception {
//        for (int i = 0; i < 10; i++) {
//            HelloWorldCommand helloWorldCommand = new HelloWorldCommand("Synchronous-hystrix");
//            String result = helloWorldCommand.execute();
//            LOGGER.info("================== result {}", result);
//        }

//        HelloWorldCommand helloWorldCommand = new HelloWorldCommand("Asynchronous-hystrix");
//        Future<String> future = helloWorldCommand.queue();
//        String result = future.get(2000, TimeUnit.MILLISECONDS);
//        LOGGER.info("================== result {}", result);
//        LOGGER.info("mainThread : {}", Thread.currentThread().getName());

        HelloWorldCommand command = new HelloWorldCommand("World");
        Observable<String> fs = command.observe();
        fs.subscribe(s -> LOGGER.info("call {}", s));
        fs.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                LOGGER.info("==================execute on Completed");
            }

            @Override
            public void onError(Throwable throwable) {
                LOGGER.info("===============onError {}", throwable.getMessage());
            }

            @Override
            public void onNext(String s) {
                LOGGER.info("=============onNext {}", s);
            }
        });
        TimeUnit.SECONDS.sleep(5);
    }

    @Test
    public void tsetThreadPool() throws Exception{
        for (int i = 0; i < 10; i++) {
            try {
                LOGGER.info("======= {}", new HystrixThreadPoolCommand("Hlx").execute());
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        }

        for (int i = 0; i < 20; i++) {
            try {
                LOGGER.info("======= {}", new HystrixThreadPoolCommand("Blx").execute());
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        }

        TimeUnit.MILLISECONDS.sleep(2000);
        LOGGER.info("===========print thead stacks ==============");
        Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
        for (Thread thread: map.keySet()) {
            LOGGER.info("****************** {}", thread.getName());
        }
        LOGGER.info("{} {}", map, map.size());
    }
}
