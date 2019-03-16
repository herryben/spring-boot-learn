package com.learn.interceptor;

import com.learn.annotation.Login;
import com.learn.bean.User;
import com.learn.controller.UserController;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter{

    @Autowired
    RedisTemplate redisTemplate;
    private Logger LOGGER = LoggerFactory.getLogger(LoginInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            if (AnnotationUtils.findAnnotation(((HandlerMethod) handler).getMethod(), Login.class) != null ||
                    AnnotationUtils.findAnnotation(((HandlerMethod) handler).getBeanType(), Login.class) != null){
                Cookie[] cookies = request.getCookies();
                String sessionKey = "";
                for (Cookie cookie: cookies) {
                    if (cookie.getName().equals(UserController.SESSION_NAME)) {
                        sessionKey = cookie.getValue();
                        break;
                    }
                }
                User user = (User) redisTemplate.opsForValue().get(UserController.SESSION_KEY_PREFIX + ":" + sessionKey);
                if (user == null || StringUtils.isBlank(user.getName())) {
                    LOGGER.error("session error");
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    return false;
                }
            }
        }
        return true;
    }
}
