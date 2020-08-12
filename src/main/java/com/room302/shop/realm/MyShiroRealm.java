package com.room302.shop.realm;

import com.alibaba.druid.util.StringUtils;
import com.room302.shop.model.SysRole;
import com.room302.shop.model.SysUser;
import com.room302.shop.service.SysRoleService;
import com.room302.shop.service.SysUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**

 * @author 刘志强
 * @created Create Time: 2019/2/16
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;

    public MyShiroRealm() {
        super();
    }

    /**
     * 认证信息，主要针对用户登录，
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken utoken = (UsernamePasswordToken) authcToken;

        //根据账号密码查用户信息
        SysUser user = new SysUser();
        user.setPwd(new String(utoken.getPassword()));
        user.setUname(utoken.getUsername());
        SysUser sysUser = sysUserService.getUserByNameAndPwd(user);
        if (null == sysUser) {
            throw new AccountException("账号不存在！");
        } else if (!StringUtils.equals(sysUser.getPwd(),new String(utoken.getPassword()))) {
            throw new AccountException("密码不正确！");
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(sysUser.getUname(),// 用户名
                sysUser.getPwd(), // 密码
                getName());
        return simpleAuthenticationInfo;
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
         SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        String userName = principals.getPrimaryPrincipal().toString().split(":")[0];
        //根据用户userName查询权限（permission) 此处省略sql写固定权限
        Set<String> permissions = new HashSet<>();

        List<SysRole> sysRoles = sysRoleService.getPermissionByName(userName);
        for (SysRole sysRole:sysRoles) {
            if (null != sysRole.getRole()){
                permissions.add(sysRole.getRole());
            }
        }

        info.setStringPermissions(permissions);
        return info;
    }
}

