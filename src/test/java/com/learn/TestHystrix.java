package com.learn;

import com.learn.command.GetUserServiceCommand;
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

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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
}
