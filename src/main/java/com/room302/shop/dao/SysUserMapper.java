package com.room302.shop.dao;

import com.room302.shop.model.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper {
    int deleteByPrimaryKey(Integer userid);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer userid);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    String getUserNmae(String name);

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    SysUser selectUserByNameAndPwd(SysUser user);

    /**
     * 用户名获取头像
     * @param uname
     * @return
     */
    String selectImgByName(String uname);
}