package com.learn.controller;

import com.learn.bean.HeaderStatus;
import com.learn.bean.HttpResponse;
import com.learn.bean.SystemPermission;
import com.learn.bean.User;
import com.learn.biz.SystemPermissionBiz;
import com.learn.biz.UserBiz;
import com.learn.service.ShiroService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class LoginController {

//    @Autowired
//    UserBiz userBiz;
//    @Autowired
//    private SystemPermissionBiz systemPermissionBiz;
//    @Autowired
//    private ShiroService shiroService;
//
//    @GetMapping("/not_login")
//    public HttpResponse notLogin() {
//        return new HttpResponse("您尚未登陆");
//    }
//
//    @GetMapping("/not_role")
//    public HttpResponse notRole() {
//        return new HttpResponse("您没有权限");
//    }
//
//    @GetMapping("/logout")
//    public HttpResponse logout() {
//        Subject subject = SecurityUtils.getSubject();
//        subject.logout();
//        return new HttpResponse("成功注销");
//    }
//
//    @PostMapping("/login")
//    public HttpResponse login(@RequestParam("user_name") String userName,
//                              @RequestParam("password") String password) {
//        Subject subject = SecurityUtils.getSubject();
//        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
//        subject.login(token);
//        User user = userBiz.getUserByName(userName);
//        String role = user != null ? user.getRole() : "";
//        if ("user".equals(role)) {
//            return new HttpResponse("欢迎登陆普通用户");
//        }
//        if ("admin".equals(role)) {
//            return new HttpResponse("欢迎来到管理员用户");
//        }
//        return new HttpResponse(HeaderStatus.UNAUTHORIZED);
//    }
//
//    @PostMapping("/permission")
//    public HttpResponse addPermission(@RequestParam("url") String url,
//                                      @RequestParam("role") String role,
//                                      @RequestParam("sort") int sort) {
//        SystemPermission systemPermission = new SystemPermission(url, role, sort, System.currentTimeMillis());
//        systemPermissionBiz.insertSystemPermission(systemPermission);
//        shiroService.updatePermission();
//        return new HttpResponse();
//    }
}
