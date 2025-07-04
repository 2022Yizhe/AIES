package com.neuswp.realms;

import com.neuswp.entity.EasPermission;
import com.neuswp.entity.EasUser;
import com.neuswp.mappers.EasPermissionMapper;
import com.neuswp.mappers.EasUserMapper;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private EasUserMapper easUserMapper;

    @Autowired
    private EasPermissionMapper easPermissionMapper;

    public void setEasUserMapper(EasUserMapper easUserMapper) {
        this.easUserMapper = easUserMapper; }

    public void setEasPermissionMapper(EasPermissionMapper easPermissionMapper) {
        this.easPermissionMapper = easPermissionMapper;
    }

    /**
     * 获取当前用户对象，查询该用户的所有权限
     * @param principalCollection 登录用户对象
     * @return 权限对象
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 1. 获取用户对象
        EasUser easUser = (EasUser)principalCollection.getPrimaryPrincipal();

        // 2. 查询用户权限
        List<EasPermission> list = easPermissionMapper.getPersByUserId(easUser.getId());

        // 3. 提取权限编码
        Set<String> set = new HashSet<>();
        for (EasPermission per : list) {
            set.add(per.getPercode());
        }

        // 4. 构造授权信息
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(set);
        return info;
    }

    /**
     * 用户认证
     * @param token 令牌
     * @return 认证信息
     * @throws AuthenticationException 认证异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 1. 获取用户名
        String username = (String) token.getPrincipal();

        // 2. 查询用户信息
        EasUser easUser = easUserMapper.getByUserName(username);

        // 3. 用户不存在处理
        if (easUser == null){
            return null; // Shiro会抛出UnknownAccountException
        }

        // 4. 构造认证信息
        AuthenticationInfo aInfo = new SimpleAuthenticationInfo(
                easUser,               // 用户对象(principal)
                easUser.getPassword(), // 数据库中的密码(credentials)
                ByteSource.Util.bytes(easUser.getSalt()), // 盐值
                "user"                // realm名称
        );

//        return new SimpleAuthenticationInfo(easUser,easUser.getPassword(),ByteSource.Util.bytes(easUser.getSalt()),"user");
        return aInfo;
    }
}
