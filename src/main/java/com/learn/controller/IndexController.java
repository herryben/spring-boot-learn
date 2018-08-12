package com.learn.controller;

import com.learn.bean.AuthorSettings;
import com.learn.bean.HttpResponse;
import com.learn.bean.User;
import com.learn.command.GetUserServiceCommand;
import com.learn.service.UserService;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
