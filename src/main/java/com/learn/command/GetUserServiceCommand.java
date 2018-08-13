package com.learn.command;

import com.learn.bean.User;
import com.learn.service.UserService;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GetUserServiceCommand extends HystrixCommand<User> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetUserServiceCommand.class);

    private UserService userService;
    private long userId;
    public GetUserServiceCommand(UserService userService, long userId) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("userService"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
                        .withRequestCacheEnabled(true)
                ));
        this.userService = userService;
        this.userId = userId;
    }

    @Override
    protected User run() throws Exception {
        LOGGER.info("=================");
        return userService.getUserById(this.userId);
    }

    @Override
    protected String getCacheKey() {
        return String.format("user-%s", this.userId);
    }
}
