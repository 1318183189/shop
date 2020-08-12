package com.room302.shop.service;

import com.room302.shop.model.SysUser;

public interface SysUserService {

    int inst(SysUser user);


    /**
     * 根据用户名获取用户信息
     * @param name
     */
    String getUserNmae(String name);

    SysUser getUserByNameAndPwd(SysUser user);

    String getImgsbyName(String uname);
}
