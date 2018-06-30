package com.learn.service;

import com.learn.bean.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "userService")
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    @Cacheable(value = "users", key = "#id")
    public User getUserById(long id) throws Exception{
        long now = System.currentTimeMillis();
        LOGGER.info("enter {}", now);
        Thread.sleep(1000);

        return new User(id, "郝瑞", now, now);
    }

}
