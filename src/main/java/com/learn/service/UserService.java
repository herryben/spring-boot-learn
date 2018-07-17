package com.learn.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.bean.User;
import com.learn.biz.UserBiz;
import com.learn.mapper.UserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserDAO, User> {
    @Autowired
    private UserBiz userBiz;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    @Cacheable(value = "users", key = "#id")
    public User getUserById(long id) throws Exception{
        long now = System.currentTimeMillis();
        LOGGER.info("enter get method {}", now);
        return baseMapper.selectById(id);
    }
    @CachePut(value = "users", key = "#user.id")
    public User updateUser(final User user) throws Exception{
        LOGGER.info("", user);
        return user;
    }
}
