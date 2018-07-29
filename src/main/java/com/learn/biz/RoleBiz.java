package com.learn.biz;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.learn.bean.Role;
import com.learn.mapper.RoleMapper;
import org.springframework.stereotype.Service;

@Service
public class RoleBiz extends ServiceImpl<RoleMapper, Role> {
}
