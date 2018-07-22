package com.learn.controller;

import com.learn.bean.AuthorSettings;
import com.learn.bean.HttpResponse;
import com.learn.bean.User;
import com.learn.service.UserService;
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
}
