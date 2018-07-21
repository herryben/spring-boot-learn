package com.learn.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.learn.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDAO extends BaseMapper<User> {
    @Select("SELECT * FROM `user` WHERE id = #{id}")
    User getUserById(@Param("id") long id);
}
