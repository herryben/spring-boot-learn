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
    public User getUserById(@PathVariable("id") long id) throws Exception{
        return userService.getUserById(id);
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public HttpResponse updateUser(@RequestBody User user) throws Exception {
        userService.updateUser(user);
        return new HttpResponse();
    }
}
