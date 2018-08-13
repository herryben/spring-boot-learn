package com.learn;

import com.learn.command.GetUserServiceCommand;
import com.learn.service.UserService;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
}
