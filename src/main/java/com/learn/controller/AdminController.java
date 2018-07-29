package com.learn.controller;

import com.learn.bean.HttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("message")
    public HttpResponse message() {
        return new HttpResponse("管理员权限");
    }
}
