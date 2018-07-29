package com.learn.controller;

import com.learn.bean.HeaderStatus;
import com.learn.bean.HttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.auth.login.AccountException;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(AccountException.class)
    public HttpResponse handleAccountException(Exception e) {
        return new HttpResponse(new HeaderStatus(500, e.getMessage()));
    }
}
