package com.learn.auth;

import com.google.common.collect.Sets;
import com.learn.bean.User;
import com.learn.biz.RoleBiz;
import com.learn.biz.UserBiz;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private UserBiz userBiz;
    @Autowired
    private RoleBiz roleBiz;
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomRealm.class);
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        LOGGER.info("————身份认证方法————");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userBiz.getUserByName(token.getUsername());
        if (user == null) {
            throw new AccountException("用户名不正确");
        } else if (!user.getPassword().equals(new String((char[]) token.getCredentials()))) {
            throw new AccountException("密码不正确");
        }
        return new SimpleAuthenticationInfo(token.getPrincipal(), user.getPassword(), getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        LOGGER.info("————权限认证方法————");
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        User user = userBiz.getUserByName(userName);
        Set<String> set = Sets.newHashSet();
        set.add(user.getRole());
        info.setRoles(set);
        return info;
    }
}
