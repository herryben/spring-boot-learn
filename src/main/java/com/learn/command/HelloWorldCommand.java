package com.learn.command;

import ch.qos.logback.core.util.TimeUtil;
import com.netflix.hystrix.*;
import com.netflix.hystrix.exception.HystrixBadRequestException;

import java.util.concurrent.TimeUnit;

public class HelloWorldCommand extends HystrixCommand<String>{
    private String name;
    public HelloWorldCommand(String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
        .andCommandKey(HystrixCommandKey.Factory.asKey("query"))
        .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("ExampleThreadPool"))
        .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(2))
        .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
        .withCircuitBreakerErrorThresholdPercentage(60)
        .withCircuitBreakerSleepWindowInMilliseconds(3000)
        .withExecutionTimeoutInMilliseconds(1000)));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
//        TimeUnit.MILLISECONDS.sleep(2000);
        if (1 == 1) {
            throw new HystrixBadRequestException("never trigger");
        }
        return "Hello " + name + Thread.currentThread().getName();
    }

    @Override
    protected String getFallback() {
        return "Fall back " + name;
    }
}
