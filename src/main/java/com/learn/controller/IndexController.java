package com.learn.controller;

import com.learn.bean.AuthorSettings;
import com.learn.bean.HttpResponse;
import com.learn.bean.User;
import com.learn.command.GetUserServiceCommand;
import com.learn.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
public class IndexController {

    @Autowired
    AuthorSettings authorSettings;
    @Autowired
    UserService userService;
    @RequestMapping("/")
    public String index(){
        return "Hello spring boot" + authorSettings.getAuthor() + " " + authorSettings.getName();
    }

    @RequestMapping("user/{id}")
    public HttpResponse getUserById(@PathVariable("id") long id) {
        return new HttpResponse(userService.getUserById(id));
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public HttpResponse updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return new HttpResponse();
    }

    @PostMapping("user")
    public HttpResponse addUser(@RequestBody User user){
        userService.addUser(user);
        return new HttpResponse();
    }

    @DeleteMapping("user/{id}")
    public HttpResponse delUser(@PathVariable long id) {
        userService.delUser(id);
        return new HttpResponse();
    }

    @GetMapping("users")
    public HttpResponse getAllUsers() {
        return new HttpResponse(userService.getAllUsers());
    }

    @GetMapping("hystrix")
    public HttpResponse getHystrix() {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        GetUserServiceCommand command1 = new GetUserServiceCommand(userService, 1);
        GetUserServiceCommand command2 = new GetUserServiceCommand(userService, 1);
        GetUserServiceCommand command3 = new GetUserServiceCommand(userService, 1);
        command1.execute();
        command2.execute();
        command3.execute();
        context.shutdown();
        return new HttpResponse();
    }

    @GetMapping("test")
    @HystrixCommand(fallbackMethod = "fallBack",
            threadPoolProperties = {
                @HystrixProperty(name = "coreSize", value = "10"),
                @HystrixProperty(name = "maxQueueSize", value = "100"),
                @HystrixProperty(name = "queueSizeRejectionThreshold", value = "20")
            },
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100"), //命令执行超时时间
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2"), //若干10s一个窗口内失败三次, 则达到触发熔断的最少请求量
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "30000") //断路30s后尝试执行, 默认为5s
            })
    public HttpResponse getTest() throws Exception {
        TimeUnit.SECONDS.sleep(3);
        return new HttpResponse();
    }

    public HttpResponse fallBack() throws Exception {
        return new HttpResponse("fallback");
    }

    @GetMapping("test/thread")
    public HttpResponse testThread() throws Exception{
        TimeUnit.SECONDS.sleep(10);
        return new HttpResponse("test thread");
    }
}
