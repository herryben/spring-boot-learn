package com.learn.controller;

import com.learn.bean.AuthorSettings;
import com.learn.bean.User;
import com.learn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
