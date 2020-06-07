package com.example.shiro.security;

import com.example.shiro.entity.User;
import com.example.shiro.mapper.UserMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class MyRealm extends AuthorizingRealm {
    public static final String SALT="123QWE";

    @Autowired
    private UserMapper mUserMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 获取用户的输入的账号.
        String username = (String) token.getPrincipal();
        // 通过username从数据库中查找
        User user = mUserMapper.getByPhone(username);
        if (user == null) {
            return null;
        }
//        if (user.getStatus() == SysUser.Status.Disabled.getCode()) {
//            throw new LockedAccountException();
//        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, // 用户名
                user.getPassword(), // 密码
                ByteSource.Util.bytes(SALT), getName() // realm name
        );

        return authenticationInfo;
    }
}
