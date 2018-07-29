package com.learn.biz;

//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.learn.bean.User;
import com.learn.mapper.UserDAO;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBiz extends ServiceImpl<UserDAO, User> {
        @Autowired
    UserDAO userDAO;

    public User getUserById(long id){
        return userDAO.selectById(id);
    }

    public User getUserByName(String name) {
        List<User> users = userDAO.selectList(new EntityWrapper<User>().eq("name", name).last("limit 1"));
        return CollectionUtils.isEmpty(users) ? null : users.get(0);
    }
}
