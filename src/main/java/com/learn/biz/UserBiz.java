package com.learn.biz;

import com.learn.bean.User;
import com.learn.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBiz {
    @Autowired
    private UserMapper userMapper;
    public User getUserById(long id){
        return userMapper.getUserById(id);
    }
}
