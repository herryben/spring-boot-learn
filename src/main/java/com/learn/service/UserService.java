package com.learn.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.learn.bean.User;
import com.learn.biz.UserBiz;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService  {

    @Autowired
    private UserBiz userBiz;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Cacheable(value = "user", key = "#id")
    public User getUserById(long id){
        LOGGER.info("getUserById id {}", id);
        return userBiz.getUserById(id);
    }

    @CachePut(value = "user", key = "#user.id")
    public User updateUser(final User user){
        LOGGER.info("updateUser user {}", user);
        userBiz.updateById(user);
        return user;
    }
    @CachePut(value = "user", key = "#result.id")
    public User addUser(User user){
        userBiz.insert(user);
        LOGGER.info("addUser user {}", user);
        return user;
    }

    @CacheEvict(value = "user", key = "#id")
    public void delUser(long id) {
        LOGGER.info("delUser id {}", id);
        userBiz.deleteById(id);
    }

    public List<User> getAllUsers() {
        LOGGER.info("getAllUsers ");
        return userBiz.selectList(new EntityWrapper<>());
    }


}
