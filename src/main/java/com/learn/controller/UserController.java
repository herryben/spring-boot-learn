package com.learn.controller;

import com.learn.bean.HttpResponse;
import com.learn.bean.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("users")
public class UserController {
    public static final String SESSION_KEY_PREFIX = "user:session";
    public static final String SESSION_NAME = "us_session";
    @Autowired
    RedisTemplate redisTemplate;
    @RequestMapping(value = "login", method = {RequestMethod.POST, RequestMethod.GET})
    public void login(@RequestParam("name") String name, @RequestParam("password") String password, HttpServletRequest request, HttpServletResponse response){
        if (StringUtils.equals(name, "haorui") && StringUtils.equals(password, password)) {
            String sessionValue = UUID.randomUUID().toString();
            User user = new User();
            user.setName("haorui");
            redisTemplate.opsForValue().set(SESSION_KEY_PREFIX + ":" + sessionValue, user);
            redisTemplate.expire(SESSION_KEY_PREFIX + ":" + sessionValue, 20, TimeUnit.MINUTES);
            Cookie cookie = new Cookie(SESSION_NAME, sessionValue);
            cookie.setDomain("localhost");
            cookie.setMaxAge(60000);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        response.setStatus(HttpStatus.OK.value());
    }

    @RequestMapping(value = "logout", method = {RequestMethod.POST, RequestMethod.GET})
    public HttpResponse logout(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        String sessionKey = "";
        for (Cookie cookie: cookies) {
            if (cookie.getName().equals(UserController.SESSION_NAME)) {
                sessionKey = cookie.getValue();
                break;
            }
        }
        redisTemplate.delete(SESSION_KEY_PREFIX + ":" + sessionKey);
        Cookie cookie = new Cookie(SESSION_NAME, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return new HttpResponse();
    }
}
