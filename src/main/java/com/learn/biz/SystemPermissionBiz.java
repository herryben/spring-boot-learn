package com.learn.biz;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.learn.bean.SystemPermission;
import com.learn.mapper.SystemPermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemPermissionBiz {
    @Autowired
    private SystemPermissionMapper systemPermissionMapper;
    public List<SystemPermission> getAllSystemPermission() {
        return systemPermissionMapper.selectList(new EntityWrapper<SystemPermission>()
        .orderBy("sort", true));
    }

    public int insertSystemPermission(SystemPermission systemPermission) {
        return systemPermissionMapper.insert(systemPermission);
    }
}
