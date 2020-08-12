package com.room302.shop.dao;

import com.room302.shop.model.SysRole;
import com.room302.shop.service.SysRoleService;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper {
    int deleteByPrimaryKey(Integer roleid);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Integer roleid);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    /**
     * 用户吗获取权限
     * @param userName
     * @return
     */
    List<SysRole> selectPermissionByName(String userName);
}