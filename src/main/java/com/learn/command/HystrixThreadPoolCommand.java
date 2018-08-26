package com.learn.command;

import com.netflix.hystrix.*;

import java.util.concurrent.TimeUnit;

public class HystrixThreadPoolCommand extends HystrixCommand<String>{
    private String name;
    public HystrixThreadPoolCommand(String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ThreadPoolTestGroup"))
        .andCommandKey(HystrixCommandKey.Factory.asKey("testCommandKey"))
        .andCommandPropertiesDefaults(
                HystrixCommandProperties.Setter()
                .withExecutionTimeoutInMilliseconds(5000)
        )
        .andThreadPoolPropertiesDefaults(
                HystrixThreadPoolProperties.Setter().withCoreSize(3)
        ));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        TimeUnit.MILLISECONDS.sleep(2000);
        return this.name;
    }

    @Override
    protected String getFallback() {
        return "fallback:" + name;
    }
}
