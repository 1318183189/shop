package com.room302.shop.service;

import com.room302.shop.model.SysRole;

import java.util.List;

public interface SysRoleService {
    /**
     * 获取用户权限
     * @param userName
     * @return
     */
    List<SysRole> getPermissionByName(String userName);
}
