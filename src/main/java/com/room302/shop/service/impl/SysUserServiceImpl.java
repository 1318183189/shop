package com.room302.shop.service.impl;

import com.room302.shop.dao.SysUserMapper;
import com.room302.shop.model.SysUser;
import com.room302.shop.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Override
    public int inst(SysUser user) {
        return sysUserMapper.insert(user);
    }

    @Override
    public String getUserNmae(String name) {
        return sysUserMapper.getUserNmae(name);
    }

    /**
     * 登录
     * @param user
     * @return
     */
    @Override
    public SysUser getUserByNameAndPwd(SysUser user) {
        return sysUserMapper.selectUserByNameAndPwd(user);
    }

    /**
     * 用户名获取头像路径
     * @param uname
     * @return
     */
    @Override
    public String getImgsbyName(String uname) {
        return sysUserMapper.selectImgByName(uname);
    }
}
