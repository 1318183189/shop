package com.room302.shop.service.impl;

import com.room302.shop.dao.SysRoleMapper;
import com.room302.shop.model.SysRole;
import com.room302.shop.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Override
    public List<SysRole> getPermissionByName(String userName) {
        return sysRoleMapper.selectPermissionByName(userName);
    }
}
