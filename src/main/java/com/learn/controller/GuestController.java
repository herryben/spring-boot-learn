package com.learn.controller;

import com.learn.bean.HttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/guest")
public class GuestController {
    @GetMapping("enter")
    public HttpResponse enter() {
        return new HttpResponse("欢迎进入，您的身份是游客");
    }
}
