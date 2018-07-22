package com.learn.biz;

//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.learn.bean.User;
import com.learn.mapper.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBiz extends ServiceImpl<UserDAO, User> {
    @Autowired
    UserDAO userDAO;
    public User getUserById(long id){
        return userDAO.selectById(id);
    }
}
